package com.bitflyer.testapp.domain.repository

import androidx.paging.PagingData
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserBriefEntity
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    val pagedListFlow: Flow<PagingData<UserBriefEntity>>
    suspend fun clearAllAndSave(userList: List<UserBriefEntity>): CallResult<Unit>
}