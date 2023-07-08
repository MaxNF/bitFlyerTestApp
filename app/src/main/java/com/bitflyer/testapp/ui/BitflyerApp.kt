package com.bitflyer.testapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bitflyer.testapp.ui.compose.navigation.Destination
import com.bitflyer.testapp.ui.compose.navigation.userDetailsScreen
import com.bitflyer.testapp.ui.compose.navigation.userListScreen

@Composable
fun BitflyerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.UserList.route) {
            userListScreen { login ->
                navController.navigate(
                    Destination.UserDetails.createRoute(login)
                )
            }
            userDetailsScreen(navController::popBackStack)
    }
}