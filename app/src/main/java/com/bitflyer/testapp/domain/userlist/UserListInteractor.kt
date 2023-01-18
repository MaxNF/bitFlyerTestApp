package com.bitflyer.testapp.domain.userlist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserListInteractor(private val repo: UserListRepository) {
    suspend fun getUsers() = withContext(Dispatchers.IO) {
        repo.getUsers()
    }
}