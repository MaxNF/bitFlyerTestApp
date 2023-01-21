package com.bitflyer.testapp.ui.userdetails.state

import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel

sealed interface UserDetailsScreenState {
    object Loading: UserDetailsScreenState
    object Error: UserDetailsScreenState
    data class Loaded(val model: UserDetailsModel): UserDetailsScreenState
}