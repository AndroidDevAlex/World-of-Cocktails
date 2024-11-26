package com.example.worldofcocktails.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.example.worldofcocktails.R
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.util.Cocktail
import com.example.worldofcocktails.util.Dimens

@Composable
fun CocktailListScreenUi(
    title: String,
    context: Context,
    screenType: ScreenType,
    bookMarkClick: (CocktailEntity) -> Unit,
    cocktailsList: LazyPagingItems<CocktailEntity>? = null,
    cocktailsData: StateUI<List<CocktailEntity>> = StateUI.Empty,
    searchWidgetState: SearchWidgetState,
    searchTextState: String = "",
    onRetry: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onClosedClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSearchTriggered: () -> Unit = {},
    isLoading: Boolean = false,
    specificCocktail: Cocktail = Cocktail.Empty,
    navigateToDetail: (CocktailEntity) -> Unit,
    showSearchIcon: Boolean
) {
    TopBarCustom(
        title = title,
        searchWidgetState = searchWidgetState,
        searchTextState = searchTextState,
        onTextChange = onTextChange,
        onClosedClicked = onClosedClicked,
        onSearchClicked = { onSearchClicked() },
        onSearchTriggered = onSearchTriggered,
        showSearchIcon = showSearchIcon,
        content = {

            Box {
                when (screenType) {
                    ScreenType.LIBRARY -> {
                        when (cocktailsData) {
                            is StateUI.Empty -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                ) {
                                    Text(
                                        text = stringResource(R.string.no_saved_cocktails_found),
                                        fontSize = Dimens.fontSize,
                                        color = Color.Gray
                                    )
                                }
                            }

                            is StateUI.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.Gray
                                    )
                                }
                            }

                            is StateUI.Success -> {
                                LazyColumn {
                                    items(cocktailsData.data) { item ->
                                        CocktailItem(
                                            item = item,
                                            bookmarkClick = bookMarkClick,
                                            onItemClick = { navigateToDetail(it) }
                                        )
                                    }
                                }
                            }

                            else -> {}
                        }
                    }

                    ScreenType.HOME -> {
                        if (searchWidgetState == SearchWidgetState.CLOSED) {
                            LazyColumn {
                                cocktailsList?.let {
                                    items(it.itemCount) { index ->
                                        cocktailsList[index]?.let { item ->
                                            CocktailItem(
                                                item = item,
                                                bookmarkClick = bookMarkClick,
                                                onItemClick = { navigateToDetail(it) }
                                            )
                                        }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                            ) {
                                LoadingStateView(
                                    loadState = cocktailsList?.loadState,
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
                                        onRetry = { onSearchClicked() },
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
            }
        }
    )
}