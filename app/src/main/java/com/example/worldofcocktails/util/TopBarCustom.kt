package com.example.worldofcocktails.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.example.worldofcocktails.ui.theme.DarkGray
import com.example.worldofcocktails.ui.theme.Orange
import com.example.worldofcocktails.ui.theme.White

@Composable
fun TopBarCustom(
    content: @Composable () -> Unit,
    title: String,
    searchWidgetState: SearchWidgetState,
    searchTextState: String = "",
    onTextChange: (String) -> Unit = {},
    onClosedClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {},
    onSearchTriggered: () -> Unit = {},
    showSearchIcon: Boolean,
    onBackClicked: () -> Unit = {}
) {
when(searchWidgetState){
    SearchWidgetState.CLOSED -> {
         ClosedAppBar(
             onSearchClicked = onSearchTriggered,
             title = title,
             showSearchIcon = showSearchIcon)
    }
    SearchWidgetState.OPENED -> {
        OpenedAppBar(
            text = searchTextState,
            onTextChange = onTextChange,
            onClosedClicked = onClosedClicked,
            onSearchClicked = onSearchClicked
        )
    }
    SearchWidgetState.OPENED_DETAIL -> {
        DetailAppBar(
            title = "Detail",
            onBackClicked = onBackClicked
        )
    }
}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = Dimens.DistanceFromBottom),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.SpacerHeight))
            content()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClosedAppBar(
    onSearchClicked: () -> Unit,
    title: String,
    showSearchIcon: Boolean
){
    TopAppBar(
        title = {
        Text(
            text = title,
            color = Orange,
            fontSize = Dimens.TopAppBarFontSize,
            textAlign = TextAlign.Left,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
    },
        actions = {
            if (showSearchIcon) {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = White
                    )
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(DarkGray)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OpenedAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClosedClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        color = DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.surfaceHeight),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = {
                        onTextChange(it)
                    },
                    placeholder = {
                        Text(
                            modifier = Modifier
                                .alpha(ContentAlpha.medium),
                            text = "Search here...",
                             color = White
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = androidx.compose.material.MaterialTheme.typography.subtitle1.fontSize,
                                color = White
                    ),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(
                            modifier = Modifier
                                .alpha(ContentAlpha.medium),
                            onClick = { onSearchClicked(text) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Orange,
                      modifier = Modifier.size(Dimens.iconModifier)
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onClosedClicked()
                                }
                            }
                        ) {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Closed Icon ",
                                        tint = Orange
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClicked(text)
                        }
                    ),
                    colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                        cursorColor = White,
                        containerColor = Color.Transparent
                    )
                )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailAppBar(
    title: String,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Orange,
                fontSize = Dimens.TopAppBarFontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(DarkGray)
    )
}