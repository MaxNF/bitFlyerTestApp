package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingData
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.biyflyer.testapp.userBriefEntityListMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UserListRepositoryImplTest @Inject constructor() : UserListRepository {
    override val pagedListFlow: Flow<PagingData<UserBriefEntity>>
        get() = flowOf(PagingData.from(userBriefEntityListMock))

    override suspend fun clearAllAndSave(userList: List<UserBriefEntity>): CallResult<Unit> {
        //do nothing
        return CallResult.Success(Unit)
    }
}