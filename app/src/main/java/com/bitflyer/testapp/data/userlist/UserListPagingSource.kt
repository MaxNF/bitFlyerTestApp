package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.domain.userlist.mapper.UserBriefEntityMapper

class UserListPagingSource(
    private val api: GithubNetworkApi,
    private val entityMapper: UserBriefEntityMapper
) : PagingSource<Int, UserBriefEntity>() {
    override fun getRefreshKey(state: PagingState<Int, UserBriefEntity>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBriefEntity> {
        val fromId = params.key ?: 1
        val loadSize = params.loadSize.coerceIn(1..100)
        return try {
            val result = api.getUsers(fromId, loadSize).map(entityMapper::map)
            LoadResult.Page(result, null, fromId + loadSize)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}