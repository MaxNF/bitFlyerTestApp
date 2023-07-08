package com.bitflyer.testapp.ui.compose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination(val route: String) {
    object UserList: Destination("user_list")
    object UserDetails: Destination("user_details/{login}") {
        const val login_arg = "account_type"
        val arguments = listOf(
            navArgument(login_arg) { type = NavType.StringType }
        )
        fun createRoute(login: String) = "user_details/$login"
    }
}