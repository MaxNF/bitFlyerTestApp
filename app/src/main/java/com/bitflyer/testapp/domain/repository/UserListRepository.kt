package com.bitflyer.testapp.domain.repository

import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity

interface UserListRepository {
    suspend fun getUsers(fromId: Int, count: Int): CallResult<List<UserBrief>>

    suspend fun getCachedUsers(): CallResult<List<UserBriefEntity>>

    suspend fun saveUsers(usersList: List<UserBriefEntity>): CallResult<Unit>

    suspend fun clearUsers(): CallResult<Unit>
}