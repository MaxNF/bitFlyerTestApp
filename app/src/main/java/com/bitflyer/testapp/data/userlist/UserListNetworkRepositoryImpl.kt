package com.bitflyer.testapp.data.userlist

import com.bitflyer.testapp.data.BaseRepo
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.repository.UserListRepository
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(private val dao: UserListDao) : BaseRepo(), UserListRepository {
    override suspend fun saveUsers(usersList: List<UserBriefEntity>) = safeApiCall {
        dao.insertAll(usersList)
    }

    override suspend fun clearUsers(): CallResult<Unit> = safeApiCall {
        dao.clearUsers()
    }
}