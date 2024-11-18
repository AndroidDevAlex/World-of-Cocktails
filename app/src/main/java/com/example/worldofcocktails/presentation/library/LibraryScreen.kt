package com.example.worldofcocktails.presentation.library


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.worldofcocktails.CocktailItem
import com.example.worldofcocktails.R
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI
import com.example.worldofcocktails.presentation.library.ScreenName.SCREEN_TITLE
import com.example.worldofcocktails.util.Dimens
import com.example.worldofcocktails.util.SearchWidgetState
import com.example.worldofcocktails.util.TopBarCustom

@Composable
fun LibraryScreen(onLaunchDetailScreen: (String) -> Unit) {
    val viewModel = hiltViewModel<LibraryViewModel>()

    val cocktails by viewModel.libraryState.collectAsState()

    LibraryScreenUi(
        cocktailsData = cocktails,
        bookMarkClick = { cocktail ->
            viewModel.deleteCocktail(cocktail)
        },
        navigateToDetail = { cocktail ->
            onLaunchDetailScreen(cocktail.idDrink)
        }
    )
}

@Composable
fun LibraryScreenUi(
    cocktailsData: StateUI<List<CocktailEntity>>,
    bookMarkClick: (CocktailEntity) -> Unit,
    navigateToDetail: (CocktailEntity) -> Unit,
) {
    TopBarCustom(
        title = SCREEN_TITLE,
        searchWidgetState = SearchWidgetState.CLOSED,
        showSearchIcon = false,
        content = {
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(cocktailsData.data) { item ->
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

                else -> {}
            }
        }
    )
}

private object ScreenName {
    const val SCREEN_TITLE = "Library"
}