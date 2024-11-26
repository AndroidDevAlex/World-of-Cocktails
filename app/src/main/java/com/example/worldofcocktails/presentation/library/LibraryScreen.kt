package com.example.worldofcocktails.presentation.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.worldofcocktails.presentation.CocktailListScreenUi
import com.example.worldofcocktails.presentation.library.ScreenName.SCREEN_TITLE
import com.example.worldofcocktails.presentation.ScreenType
import com.example.worldofcocktails.presentation.SearchWidgetState

@Composable
fun LibraryScreen(onLaunchDetailScreen: (String) -> Unit) {
    val viewModel = hiltViewModel<LibraryViewModel>()
    val context = LocalContext.current

    val cocktails by viewModel.libraryState.collectAsState()

    CocktailListScreenUi(
        title = SCREEN_TITLE,
        context = context,
        screenType = ScreenType.LIBRARY,
        bookMarkClick = { cocktail ->
            viewModel.deleteCocktail(cocktail)
        },
        cocktailsData = cocktails,
        searchWidgetState = SearchWidgetState.CLOSED,
        navigateToDetail = { cocktail ->
            onLaunchDetailScreen(cocktail.idDrink)
        },
        showSearchIcon = false
    )
}

private object ScreenName {
    const val SCREEN_TITLE = "Library"
}