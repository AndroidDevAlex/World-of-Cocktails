package com.example.worldofcocktails.navigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){

    data object Home : BottomBarScreen("home", "Home", Icons.Default.Home)

    data object Library : BottomBarScreen("library", "Library", Icons.Default.List)

}