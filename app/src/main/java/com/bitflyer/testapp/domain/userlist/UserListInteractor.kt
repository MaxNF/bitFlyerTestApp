package com.bitflyer.testapp.domain.userlist

class UserListInteractor(private val repo: UserListRepository) {
    suspend fun getUsers(fromId: Int, count: Int) = repo.getUsers(fromId, count)
}