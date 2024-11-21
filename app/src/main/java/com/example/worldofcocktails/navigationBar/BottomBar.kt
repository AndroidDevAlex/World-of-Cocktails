package com.example.worldofcocktails.navigationBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.worldofcocktails.ui.theme.DarkGray
import com.example.worldofcocktails.ui.theme.Orange
import com.example.worldofcocktails.ui.theme.White

@Composable
fun BottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Library
    )

    BottomNavigation(
        backgroundColor = DarkGray
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation Icon") },
        selected = currentRoute == screen.route,
        selectedContentColor = Orange,
        unselectedContentColor = White,
        onClick = { onNavigate(screen.route) }
    )
}