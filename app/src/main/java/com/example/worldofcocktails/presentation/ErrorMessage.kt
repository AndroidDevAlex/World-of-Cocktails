package com.example.worldofcocktails.presentation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.worldofcocktails.R
import com.example.worldofcocktails.ui.theme.Red
import com.example.worldofcocktails.util.Dimens
import retrofit2.HttpException
import java.io.IOException

@Composable
fun ErrorMessage(
    throwable: Throwable,
    context: Context,
    onRetry: () -> Unit,
    icon: Painter? = null,
    backgroundColor: Color,
    titleText: String? = null,
    titleColor: Color,
    errorTextSize: TextUnit,
    contextType: ErrorContext
) {
    val message = getErrorMessage(throwable, context, contextType)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.padding)
            .background(backgroundColor)
            .padding(Dimens.padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = "Error Icon",
                    tint = Red,
                    modifier = Modifier.size(Dimens.iconSize)
                )
            }
            Spacer(modifier = Modifier.height(Dimens.spacerHeight8))

        titleText?.let {
            Text(
                text = it,
                fontSize = Dimens.errorTitleTextSize,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }
            Spacer(modifier = Modifier.height(Dimens.spacerHeight4))

        Text(
            text = message,
            fontSize = errorTextSize,
            color = titleColor
        )
        Spacer(modifier = Modifier.height(Dimens.spacerHeight8))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}

private fun getErrorMessage(exception: Throwable, context: Context, contextType: ErrorContext): String {
    return when (contextType) {
        ErrorContext.ONE_COCKTAIL -> {
            when (exception) {
                is IOException -> context.getString(R.string.check_your_internet_connection)
                is HttpException -> context.getString(R.string.server_error_please_try_again_later)
                else -> context.getString(R.string.the_cocktail_was_not_found_please_try_again)
            }
        }
        ErrorContext.LIST_COCKTAILS -> {
            when (exception) {
                is IOException -> context.getString(R.string.check_your_internet_connection)
                is HttpException -> context.getString(R.string.server_issues_please_try_again_later)
                else -> context.getString(R.string.an_unknown_error_occurred)
            }
        }
        ErrorContext.DETAIL_COCKTAIL -> {
            when (exception) {
                is IOException -> context.getString(R.string.check_your_internet_connection)
                is HttpException -> context.getString(R.string.server_issues_please_try_again_later)
                else -> context.getString(R.string.an_unknown_error_occurred)
            }
        }
    }
}