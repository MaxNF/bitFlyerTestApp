package com.bitflyer.testapp.ui.compose.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bitflyer.testapp.ui.compose.userdetails.UserDetailsScreen
import com.bitflyer.testapp.ui.compose.userlist.UserListScreen
import com.bitflyer.testapp.ui.userdetails.UserDetailsViewModel
import com.bitflyer.testapp.ui.userlist.UserListViewModel

fun NavGraphBuilder.userListScreen(onDetailsClick: (String) -> Unit) {
        composable(Destination.UserList.route) {
            val viewModel: UserListViewModel = hiltViewModel()
             UserListScreen(viewModel.flow, onDetailsClick)
        }
    }

fun NavGraphBuilder.userDetailsScreen(login: String, onBackClick: () -> Unit) {
    composable(Destination.UserDetails.route, arguments = Destination.UserDetails.arguments) {
        val viewModel: UserDetailsViewModel = hiltViewModel()
        viewModel.fetchDetails(login)
        UserDetailsScreen(onReloadClick = viewModel::fetchDetails, onBackClick)
    }
}