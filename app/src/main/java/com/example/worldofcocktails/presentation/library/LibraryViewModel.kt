package com.example.worldofcocktails.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldofcocktails.domain.useCases.libraryCase.DeleteCocktailUseCase
import com.example.worldofcocktails.domain.useCases.libraryCase.GetCocktailsByDBUseCase
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getCocktailsByDBUseCase: GetCocktailsByDBUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val deleteCocktailUseCase: DeleteCocktailUseCase,
) : ViewModel() {

    private val _libraryState = MutableStateFlow<StateUI<List<CocktailEntity>>>(StateUI.Empty)
    val libraryState: StateFlow<StateUI<List<CocktailEntity>>> = _libraryState


    init {
        getSavedCocktails()
    }

    private fun getSavedCocktails() {
        viewModelScope.launch(ioDispatcher) {
            _libraryState.value = StateUI.Loading
            getCocktailsByDBUseCase.getOllCocktails().collect { cocktails ->
                _libraryState.value = if (cocktails.isEmpty()) {
                    StateUI.Empty
                } else {
                    StateUI.Success(cocktails)
                }
            }
        }
    }

    fun deleteCocktail(cocktail: CocktailEntity) {
        viewModelScope.launch(ioDispatcher) {
            _libraryState.value = StateUI.Loading
            deleteCocktailUseCase.deleteCocktail(cocktail.idDrink)

            val allCocktails = getCocktailsByDBUseCase.getOllCocktails().first()

            if (allCocktails.isEmpty()) {
                _libraryState.value = StateUI.Empty
            } else {
                _libraryState.value = StateUI.Success(allCocktails)
            }
        }
    }
}