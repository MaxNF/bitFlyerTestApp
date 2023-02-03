package com.bitflyer.testapp.data.userlist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserListPagingSource(
    private val api: GithubNetworkApi,
    private val dao: UserListDao,
    private val entityMapper: BaseMapper<UserBrief, UserBriefEntity>,
    private var restoreState: Boolean
) : PagingSource<Int, UserBriefEntity>() {
    companion object {
        private const val TAG = "UserListPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, UserBriefEntity>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBriefEntity> = withContext(Dispatchers.IO) {
        val fromId = params.key ?: 1
        val loadSize = params.loadSize.coerceIn(1..100)
        try {
            if (restoreState) {
                restoreState = false

                val users = try {
                    dao.getUsers()
                } catch (e: Exception) {
                    Log.e(TAG, "error while accessing database", e)
                    null
                }

                if (users.isNullOrEmpty()) loadFromNet(fromId, loadSize)
                else LoadResult.Page(users, null, users.last().id + 1)
            } else {
                loadFromNet(fromId, loadSize)
            }
        } catch (e: Exception) {
            Log.e(TAG, "error while fetching data from github api", e)
            LoadResult.Error(e)
        }
    }

    private suspend fun loadFromNet(fromId: Int, loadSize: Int): LoadResult<Int, UserBriefEntity> {
        val users = api.getUsers(fromId, loadSize).map(entityMapper::map)
        return LoadResult.Page(users, null, fromId + loadSize)
    }
}