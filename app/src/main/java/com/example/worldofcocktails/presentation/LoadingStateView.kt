package com.example.worldofcocktails.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.example.worldofcocktails.R
import com.example.worldofcocktails.ui.theme.Beige
import com.example.worldofcocktails.ui.theme.DarkRed
import com.example.worldofcocktails.ui.theme.LightGray
import com.example.worldofcocktails.ui.theme.Orange
import com.example.worldofcocktails.ui.theme.Red
import com.example.worldofcocktails.util.Dimens

@Composable
fun LoadingStateView(
    loadState: CombinedLoadStates? = null,
    isLoading: Boolean = false,
    context: Context,
    onRetry: () -> Unit,
    error: Throwable? = null,
    contextType: ErrorContext
) {
    val loadingState = loadState ?: if (isLoading) {
        CombinedLoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
    } else {
        CombinedLoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
    }

    when {
        loadingState.refresh is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                LoadingShimmerEffect()
            }
        }
        loadingState.refresh is LoadState.Error -> {
            val e = (loadingState.refresh as LoadState.Error).error
            ErrorMessage(
                throwable = e,
                context = context,
                onRetry = onRetry,
                icon = painterResource(id = R.drawable.error),
                backgroundColor = Beige,
                titleText = stringResource(R.string.an_error_occurred),
                titleColor = Red,
                errorTextSize = Dimens.errorTextSize,
                contextType = contextType
            )
        }
        loadingState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.padding)
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    color = Orange,
                    modifier = Modifier.size(Dimens.circularProgress)
                )
            }
        }
        loadingState.append is LoadState.Error -> {
            val e = (loadingState.append as LoadState.Error).error
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.padding)
                    .wrapContentSize(Alignment.Center)
            ) {
                ErrorMessage(
                    throwable = e,
                    context = context,
                    onRetry = onRetry,
                    null,
                    backgroundColor = LightGray.copy(alpha = 0.9f),
                    null,
                    titleColor = DarkRed,
                    errorTextSize = Dimens.errorTextSize20,
                    contextType = contextType
                )
            }
        }
        error != null -> {
            ErrorMessage(
                throwable = error,
                context = context,
                onRetry = onRetry,
                icon = painterResource(id = R.drawable.error),
                backgroundColor = Beige,
                titleText = stringResource(R.string.an_error_occurred),
                titleColor = Red,
                errorTextSize = Dimens.errorTextSize,
                contextType = contextType
            )
        }
    }
}