package com.example.narutoapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.narutoapp.screens.narutodetail.NarutoDetailScreen
import com.example.narutoapp.screens.narutolist.NarutoListScreen
import com.example.pokedexcompose.screens.home.BottomBarScreen
import com.example.pokedexcompose.screens.universal.ScreenContent

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = "home_graph",
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(AppScreen.ListScreen.route) {
            NarutoListScreen(navController = navController)
        }
        composable(
            "${AppScreen.DetailScreen.route}/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.StringType
                }
            )
        ) {
            val characterId = remember {
                it.arguments?.getString("characterId")
            }
            NarutoDetailScreen(
                navController = navController,
                characterId = characterId ?: ""
            )
        }
        authNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = AppScreen.App.route,
        startDestination = AppScreen.SingIn.route
    ) {
        composable(route = AppScreen.SingIn.route) {
            ScreenContent(name = AppScreen.SingIn.route) {

            }
        }
        composable(route = AppScreen.SingUp.route) {
            ScreenContent(name = AppScreen.SingUp.route) {

            }
        }
        composable(route = AppScreen.Profile.route) {
            ScreenContent(name = AppScreen.Profile.route) {

            }
        }
    }
}

sealed class AppScreen(val route: String) {
    object ListScreen : AppScreen(route = "naruto_list_screen")
    object DetailScreen : AppScreen(route = "naruto_detail_screen")
    object App : AppScreen(route = "auth")
    object Profile : AppScreen(route = "profile")
    object SingIn : AppScreen(route = "singIn")
    object SingUp : AppScreen(route = "singUp")
}