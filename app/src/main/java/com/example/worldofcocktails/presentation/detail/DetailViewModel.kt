package com.example.worldofcocktails.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldofcocktails.domain.useCases.detailCase.GetCocktailDetailUseCase
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val getCocktailDetailUseCase: GetCocktailDetailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _screenState = MutableStateFlow<StateUI<CocktailEntity>>(StateUI.Loading)
    val screenState: StateFlow<StateUI<CocktailEntity>> = _screenState

    private val cocktailId: String = savedStateHandle["id"] ?: error("Cocktail ID is missing")
    private val fromLibrary: Boolean = savedStateHandle["fromLibrary"] ?: false

    init {
        loadCocktailDetail()
    }

    fun loadCocktailDetail() {
        viewModelScope.launch(ioDispatcher) {
            _screenState.value = StateUI.Loading
            try {
                val cocktail = if (fromLibrary) {
                    getCocktailDetailUseCase.getCocktailFromDB(cocktailId).toState()
                } else {
                    getCocktailDetailUseCase.getCocktailFromApi(cocktailId).toState()
                }
                _screenState.value = cocktail
            } catch (e: Exception) {
                _screenState.value = StateUI.Error(e)
            }
        }
    }
}