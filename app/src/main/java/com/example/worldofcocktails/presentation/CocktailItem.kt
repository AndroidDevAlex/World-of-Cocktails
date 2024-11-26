package com.example.worldofcocktails.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.worldofcocktails.entityUi.CocktailEntity
import com.example.worldofcocktails.ui.theme.Gray
import com.example.worldofcocktails.util.Dimens
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.worldofcocktails.R
import com.example.worldofcocktails.ui.theme.DarkRed

@Composable
fun CocktailItem(
    item: CocktailEntity,
    bookmarkClick: (CocktailEntity) -> Unit,
    onItemClick: (CocktailEntity) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingBetween)
            .background(Gray)
            .clickable(onClick = { onItemClick(item) })
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                ImageAndContent(item)
            }
            SaveItem(item = item, onBookmarkClick = bookmarkClick)

        }
    }
}

@Composable
private fun ImageAndContent(
    cocktail: CocktailEntity

) {
    Row{
        Image(cocktail)
        Content(cocktail)
    }
}

@Composable
private fun Image(item: CocktailEntity) {
    item.image.let { imageUrl ->
        Box(
            modifier = Modifier
                .border(Dimens.Border, Color.Black)
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = imageUrl, contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(Dimens.ImageSize)
            )
        }
    }
}

@Composable
private fun Content(item: CocktailEntity) {
    Column(
        modifier = Modifier
            .padding(
                top = Dimens.PaddingTemplate,
                start = Dimens.PaddingTemplate
            )
    ) {
        Text(
            text = item.name,
            style = TextStyle(
                fontSize = Dimens.TextFontSizeTitle,
                color = DarkRed,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item.category,
            style = TextStyle(
                fontSize = Dimens.textFontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item.alcoholType,
            style = TextStyle(
                fontSize = Dimens.textFontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun SaveItem(
    item: CocktailEntity,
    onBookmarkClick: (CocktailEntity) -> Unit
) {

    val bookmarkIcon = if (item.isBookmarked) {
        painterResource(id = R.drawable.delete)
    } else {
        painterResource(id = R.drawable.bookmark )
    }

    val tint = Color.White

    Column(horizontalAlignment = Alignment.End) {

        Spacer(modifier = Modifier.height(Dimens.Height))
        Icon(
            painter = bookmarkIcon,
            contentDescription = "Bookmark",
            tint = tint,
            modifier = Modifier
                .clickable {
                    onBookmarkClick(item)
                }
        )
    }
}