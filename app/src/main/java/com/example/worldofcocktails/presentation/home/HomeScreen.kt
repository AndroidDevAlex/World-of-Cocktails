package com.example.worldofcocktails.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.worldofcocktails.CocktailItem
import com.example.worldofcocktails.R
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.util.ErrorContext
import com.example.worldofcocktails.util.LoadingStateView
import com.example.worldofcocktails.util.SearchWidgetState
import com.example.worldofcocktails.util.TopBarCustom

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

    HomeScreenUi(
        bookMarkClick = { cocktail -> viewModel.pressBookmark(cocktail) },
        context = context,
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
        }
    )
}

@Composable
fun HomeScreenUi(
    bookMarkClick: (CocktailEntity) -> Unit,
    context: Context,
    cocktailsList: LazyPagingItems<CocktailEntity>,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onRetry: () -> Unit,
    onTextChange: (String) -> Unit,
    onClosedClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    isLoading: Boolean,
    specificCocktail: Cocktail,
    navigateToDetail: (CocktailEntity) -> Unit,
) {
    TopBarCustom(
        title = ScreenName.SCREEN_TITLE,
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onTextChange = onTextChange,
        onClosedClicked = onClosedClicked,
        onSearchClicked = { onSearchClicked() },
        onSearchTriggered = onSearchTriggered,
        showSearchIcon = true,
        content = {
            Box {
                if (searchWidgetState == SearchWidgetState.CLOSED) {
                    LazyColumn {
                        items(cocktailsList.itemCount) { index ->
                            cocktailsList[index]?.let { item ->
                                CocktailItem(
                                    item = item,
                                    bookmarkClick = bookMarkClick,
                                    onItemClick = {
                                        navigateToDetail(it)
                                    }
                                )
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)) {
                        LoadingStateView(
                            loadState = cocktailsList.loadState,
                            context = context,
                            onRetry = onRetry,
                            contextType = ErrorContext.LIST_COCKTAILS
                        )
                    }
                } else {
                    when {
                        isLoading -> {
                            LoadingStateView(
                                isLoading = true,
                                context = context,
                                onRetry = {},
                                contextType = ErrorContext.ONE_COCKTAIL
                            )
                        }

                        specificCocktail is Cocktail.Error -> {
                            LoadingStateView(
                                error = specificCocktail.exception,
                                context = context,
                                onRetry = {onSearchClicked()},
                                contextType = ErrorContext.ONE_COCKTAIL
                            )
                        }

                        specificCocktail is Cocktail.Empty -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            ) {
                                Text(
                                    text = stringResource(R.string.no_cocktail_found),
                                    color = Color.Gray
                                )
                            }
                        }
                        specificCocktail is Cocktail.Success -> {
                            LazyColumn {
                                item {
                                    (specificCocktail as? Cocktail.Success)?.cocktail?.let {
                                        CocktailItem(
                                            item = it,
                                            bookmarkClick = bookMarkClick,
                                            onItemClick = { cocktail ->
                                                navigateToDetail(cocktail)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

private object ScreenName {
    const val SCREEN_TITLE = "Home"
}