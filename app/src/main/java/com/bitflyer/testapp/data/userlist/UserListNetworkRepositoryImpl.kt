package com.bitflyer.testapp.data.userlist

import androidx.paging.Pager
import androidx.paging.PagingData
import com.bitflyer.testapp.data.BaseRepo
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.repository.UserListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(
    private val dao: UserListDao,
    private val pager: Pager<Int, UserBriefEntity>
) : BaseRepo(), UserListRepository {

    override val pagedListFlow: Flow<PagingData<UserBriefEntity>>
        get() = pager.flow

    override suspend fun clearAllAndSave(userList: List<UserBriefEntity>): CallResult<Unit> = safeApiCall {
        dao.clearAndSaveUsers(userList)
    }
}