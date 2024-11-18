package com.example.worldofcocktails.presentation.detail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.worldofcocktails.R
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.presentation.StateUI
import com.example.worldofcocktails.ui.theme.Beige
import com.example.worldofcocktails.util.Dimens
import com.example.worldofcocktails.util.ErrorContext
import com.example.worldofcocktails.util.ErrorMessage
import com.example.worldofcocktails.util.SearchWidgetState
import com.example.worldofcocktails.util.TopBarCustom

@Composable
fun DetailScreen(
    cocktailId: String,
    goToBack: () -> Unit,
    fromLibrary: Boolean,
) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(cocktailId) {
        if (fromLibrary) {
            viewModel.loadCocktailDetailFromDb(cocktailId)
        } else {
            viewModel.loadCocktailDetailFromServer(cocktailId)
        }
    }

    DetailScreenUi(
        screenState,
        context = context,
        onBackClick = { goToBack() },
        repeatRequest = { viewModel.loadCocktailDetailFromServer(cocktailId) }
    )
}

@Composable
fun DetailScreenUi(
    screenState: StateUI<CocktailEntity>,
    context: Context,
    onBackClick: () -> Unit,
    repeatRequest: () -> Unit
) {
    TopBarCustom(
        title = ScreenName.SCREEN_TITLE,
        searchWidgetState = SearchWidgetState.OPENED_DETAIL,
        onBackClicked = onBackClick,
        showSearchIcon = false,
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                when (screenState) {
                    is StateUI.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                        is StateUI.Success -> {
                        val cocktail = screenState.data
                        val scrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(Dimens.padding)
                                .verticalScroll(scrollState)
                                .padding(bottom = Dimens.paddingBottom)
                        ) {
                            Text(
                                text = cocktail.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth() 
                                    .padding(bottom = Dimens.paddingTextBottom),
                                textAlign = TextAlign.Center
                            )

                            cocktail.image?.let { imageUrl ->
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Cocktail Image",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = Dimens.paddingAsyncImage)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimens.paddingVertical),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Category: ",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(end = Dimens.paddingEnd)
                                )
                                Text(
                                    text = cocktail.category,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimens.paddingVertical),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Alcohol Type: ",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(end = Dimens.paddingEnd)
                                )
                                Text(
                                    text = cocktail.alcoholType,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimens.paddingVertical),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Glass Type: ",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(end = Dimens.paddingEnd)
                                )
                                cocktail.detail?.let {
                                    Text(
                                        text = it.glassType,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            Text(
                                text = "Instructions:",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(vertical = Dimens.textVertical)
                            )
                            cocktail.detail?.let {
                                Text(
                                    text = it.instructions,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = Dimens.padding)
                                )
                            }
                            Text(
                                text = "Ingredients:",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = Dimens.paddingBottom)
                            )

                            cocktail.detail?.ingredients?.forEach { ingredient ->
                                val recipeDrink = buildAnnotatedString {

                                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                        append("${ingredient.nameIngredient}: ")
                                    }
                                    append(ingredient.measure)
                                }

                                Text(
                                    text = recipeDrink,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = Dimens.textPaddingBottom)
                                )
                            }
                        }
                    }
                        is StateUI.Error -> {
                        ErrorMessage(
                            throwable = screenState.exception,
                            context = context,
                            onRetry = { repeatRequest() },
                            icon = painterResource(id = R.drawable.error),
                            backgroundColor = Beige,
                            titleText = null,
                            titleColor = Color.Red,
                            errorTextSize = Dimens.errorTextSize,
                            contextType = ErrorContext.DETAIL_COCKTAIL
                        )
                    }

                    else -> {}
                }
            }

        }
    )

}

private object ScreenName {
    const val SCREEN_TITLE = "Detail"
}