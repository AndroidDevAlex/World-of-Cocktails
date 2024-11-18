package com.example.worldofcocktails.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldofcocktails.domain.useCases.detailCase.GetCocktailDetailUseCase
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI
import com.example.worldofcocktails.util.toState
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
    private val getCocktailDetailUseCase: GetCocktailDetailUseCase
): ViewModel() {

    private val _screenState = MutableStateFlow<StateUI<CocktailEntity>>(StateUI.Loading)
    val screenState: StateFlow<StateUI<CocktailEntity>> = _screenState


    fun loadCocktailDetailFromServer(cocktailId: String) {
        viewModelScope.launch(ioDispatcher) {
            _screenState.value = StateUI.Loading
            try {
                val cocktail = getCocktailDetailUseCase.getCocktailsFromApi(cocktailId)
                _screenState.value = cocktail.toState()
            } catch (e: Exception) {
                _screenState.value = StateUI.Error(e)
            }
        }
    }

    fun loadCocktailDetailFromDb(cocktailId: String) {
        viewModelScope.launch(ioDispatcher) {
            _screenState.value = StateUI.Loading
            try {
                val cocktail = getCocktailDetailUseCase.getCocktailFromDB(cocktailId)
                _screenState.value = cocktail.toState()
            } catch (e: Exception) {
                _screenState.value = StateUI.Error(e)
            }
        }
    }
}