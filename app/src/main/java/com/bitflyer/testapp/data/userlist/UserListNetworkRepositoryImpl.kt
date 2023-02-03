package com.bitflyer.testapp.data.userlist

import com.bitflyer.testapp.data.BaseRepo
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.userlist.UserListRepository
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(
    private val api: GithubNetworkApi,
    private val dao: UserListDao
) : BaseRepo(), UserListRepository {
    override suspend fun getUsers(fromId: Int, count: Int): CallResult<List<UserBrief>> = safeApiCall {
        api.getUsers(fromId, count)
    }

    override suspend fun getCachedUsers(): CallResult<List<UserBriefEntity>> = safeApiCall {
        dao.getUsers()
    }

    override suspend fun saveUsers(usersList: List<UserBriefEntity>) = safeApiCall {
        dao.insertAll(usersList)
    }

    override suspend fun clearUsers(): CallResult<Unit> = safeApiCall {
        dao.clearUsers()
    }
}