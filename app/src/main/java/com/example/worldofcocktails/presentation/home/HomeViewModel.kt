package com.example.worldofcocktails.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.worldofcocktails.domain.useCases.homeCase.GetCocktailByNameUseCase
import com.example.worldofcocktails.domain.useCases.homeCase.GetCocktailsByApiUseCase
import com.example.worldofcocktails.domain.useCases.homeCase.SaveCocktailUseCase
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.util.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCocktailsByApiUseCase: GetCocktailsByApiUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val saveCocktailUseCase: SaveCocktailUseCase,
    private val getCocktailByNameUseCase: GetCocktailByNameUseCase
) : ViewModel() {

    private val _searchSpecificCocktail = MutableStateFlow<Cocktail>(Cocktail.Empty)
    val searchSpecificCocktail: StateFlow<Cocktail> = _searchSpecificCocktail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _pagedCocktails = MutableStateFlow<PagingData<CocktailEntity>>(PagingData.empty())
    val pagedCocktails: StateFlow<PagingData<CocktailEntity>> = _pagedCocktails

    private val _bookmarkEvent = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val bookmarkEvent: SharedFlow<Boolean> = _bookmarkEvent.asSharedFlow()

    private val _searchWidgetState = MutableStateFlow(SearchWidgetState.CLOSED)
    val searchWidgetState: StateFlow<SearchWidgetState> = _searchWidgetState

    private val _searchTextState  = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchTextState


    fun updateSearchWidgetState(newValue: SearchWidgetState){
        _searchWidgetState.value = newValue
    }

    fun updateSearchStateTex(newValue: String){
        _searchTextState.value = newValue
    }

    init {
        fetchPagedCocktails()
    }

    private fun fetchPagedCocktails() {
        viewModelScope.launch(ioDispatcher) {
            getCocktailsByApiUseCase.getCocktails()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _pagedCocktails.value = pagingData
                }
        }
    }

    fun searchCocktailByName(name: String) {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            val result = getCocktailByNameUseCase.searchCocktailByName(name)
            _searchSpecificCocktail.value = result
            _isLoading.value = false
        }
    }

    fun clearSearch() {
        _searchWidgetState.value = SearchWidgetState.CLOSED
        _searchSpecificCocktail.value = Cocktail.Empty
    }

    fun pressBookmark(cocktail: CocktailEntity) {
        viewModelScope.launch(ioDispatcher) {
            val isAlreadySaved = saveCocktailUseCase.isCocktailSaved(cocktail.idDrink)
            if (isAlreadySaved) {
                _bookmarkEvent.emit(true)
            } else {
                saveCocktailUseCase.saveCocktail(cocktail)
                _bookmarkEvent.emit(false)
            }
        }
    }
}