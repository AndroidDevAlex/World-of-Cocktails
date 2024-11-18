package com.example.worldofcocktails.navigationBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.worldofcocktails.presentation.detail.DetailScreen
import com.example.worldofcocktails.presentation.home.HomeScreen
import com.example.worldofcocktails.presentation.library.LibraryScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {

    NavHost(navController = navHostController, startDestination = BottomBarScreen.Home.route) {

        composable(BottomBarScreen.Library.route) {
            LibraryScreen(
                onLaunchDetailScreen = { cocktailId ->
                    navHostController.navigate("cocktail/$cocktailId?fromLibrary=true")
                }
            )
        }
        composable(BottomBarScreen.Home.route) {
            HomeScreen(
                onLaunchDetailScreen = { cocktailId ->
                    navHostController.navigate("cocktail/$cocktailId?fromLibrary=false")
                }
            )
        }
        composable(
            route = "cocktail/{id}?fromLibrary={fromLibrary}",
            arguments = listOf(
                navArgument("fromLibrary") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("id") ?: return@composable
            val fromLibrary = backStackEntry.arguments?.getBoolean("fromLibrary") ?: false

            DetailScreen(
                cocktailId = cocktailId,
                fromLibrary = fromLibrary,
                goToBack = { navHostController.popBackStack() }
            )
        }
    }
}