package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.domain.userlist.mapper.UserBriefToUserBriefEntityMapper

class UserListPagingSource(
    private val api: GithubNetworkApi,
    private val dao: UserListDao,
    private val entityMapper: UserBriefToUserBriefEntityMapper,
    private var restoreState: Boolean
) : PagingSource<Int, UserBriefEntity>() {
    override fun getRefreshKey(state: PagingState<Int, UserBriefEntity>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBriefEntity> {
        val fromId = params.key ?: 1
        val loadSize = params.loadSize.coerceIn(1..100)
        return try {
            if (restoreState) {
                restoreState = false

                val users = try {
                    dao.getUsers()
                } catch (e: Exception) {
                    null
                }

                if (users.isNullOrEmpty()) loadFromNet(fromId, loadSize)
                else LoadResult.Page(users, null, users.last().id + 1 + loadSize)
            } else {
                loadFromNet(fromId, loadSize)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun loadFromNet(fromId: Int, loadSize: Int): LoadResult<Int, UserBriefEntity> {
        val users = api.getUsers(fromId, loadSize).map(entityMapper::map)
        return LoadResult.Page(users, null, fromId + loadSize)
    }
}