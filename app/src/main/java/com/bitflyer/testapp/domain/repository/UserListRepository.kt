package com.bitflyer.testapp.domain.repository

import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserBriefEntity

interface UserListRepository {
    suspend fun saveUsers(usersList: List<UserBriefEntity>): CallResult<Unit>

    suspend fun clearUsers(): CallResult<Unit>
}