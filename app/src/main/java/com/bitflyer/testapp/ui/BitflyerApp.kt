package com.bitflyer.testapp.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitflyer.testapp.ui.compose.navigation.Destination
import com.bitflyer.testapp.ui.compose.navigation.userDetailsScreen
import com.bitflyer.testapp.ui.compose.navigation.userListScreen

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BitflyerApp() {
    val navController = rememberNavController()
    val currentEntry = navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            TopBar(currentEntry.value, navController)
        }
    ) {
        NavHost(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it), navController = navController, startDestination = Destination.UserList.route
        ) {
            userListScreen { login ->
                navController.navigate(
                    Destination.UserDetails.createRoute(login)
                )
            }
            userDetailsScreen(navController::popBackStack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(entry: NavBackStackEntry?, navController: NavHostController) {
    val currentRoute = entry?.destination?.route ?: Destination.UserList.route
    TopAppBar(
        navigationIcon = {
            if (currentRoute != Destination.UserList.route) {
                IconButton(onClick = navController::popBackStack) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
            }
        },
        title = {
            when (currentRoute) {
                Destination.UserList.route -> {
                    Text("Github Users")
                }

                Destination.UserDetails.route -> {
                    val login = navController.currentBackStackEntry?.arguments?.getString("login") ?: ""
                    Row {
                        Text(login)
                    }
                }
            }
        })
}