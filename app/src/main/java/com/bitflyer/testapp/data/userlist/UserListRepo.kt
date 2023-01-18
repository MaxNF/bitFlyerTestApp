package com.bitflyer.testapp.data.userlist

import com.bitflyer.testapp.data.BaseRepo
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.UserListRepository

class UserListRepositoryImpl(private val api: GithubNetworkApi): BaseRepo(), UserListRepository {
    override suspend fun getUsers(fromId: Int, count: Int): List<UserBrief> = performWithRetry {
        api.getUsers(fromId, count)
    }
}