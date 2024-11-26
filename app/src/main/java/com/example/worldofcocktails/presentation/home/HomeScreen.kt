package com.example.worldofcocktails.presentation.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.worldofcocktails.R
import com.example.worldofcocktails.presentation.CocktailListScreenUi
import com.example.worldofcocktails.presentation.ScreenType
import com.example.worldofcocktails.presentation.SearchWidgetState

@Composable
fun HomeScreen(onLaunchDetailScreen: (String) -> Unit) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current

    val listCocktailsPaging = viewModel.pagedCocktails.collectAsLazyPagingItems()
    val searchWidgetState by viewModel.searchWidgetState.collectAsState()
    val searchTextState by viewModel.searchText.collectAsState()
    val searchSpecificCocktail by viewModel.searchSpecificCocktail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.bookmarkEvent.collect { message ->
            val updateMessage = if (message) {
                context.getString(R.string.cocktail_already_saved)
            } else {
                context.getString(R.string.cocktail_saved)
            }
            Toast.makeText(context, updateMessage, Toast.LENGTH_SHORT).show()
        }
    }

    CocktailListScreenUi(
        title = ScreenName.SCREEN_TITLE,
        context = context,
        screenType = ScreenType.HOME,
        bookMarkClick = { cocktail -> viewModel.pressBookmark(cocktail) },
        cocktailsList = listCocktailsPaging,
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onRetry = { listCocktailsPaging.retry() },
        onTextChange = { viewModel.updateSearchStateTex(it) },
        onClosedClicked = { viewModel.clearSearch() },
        onSearchClicked = { viewModel.searchCocktailByName(searchTextState) },
        onSearchTriggered = { viewModel.updateSearchWidgetState(SearchWidgetState.OPENED) },
        isLoading = isLoading,
        specificCocktail = searchSpecificCocktail,
        navigateToDetail = { cocktail ->
            onLaunchDetailScreen(cocktail.idDrink)
        },
        showSearchIcon = true
    )
}

private object ScreenName {
    const val SCREEN_TITLE = "Home"
}