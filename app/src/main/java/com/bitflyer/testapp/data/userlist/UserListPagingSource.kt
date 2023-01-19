package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief

class UserListPagingSource(private val api: GithubNetworkApi) : PagingSource<Int, UserBrief>(){
    override fun getRefreshKey(state: PagingState<Int, UserBrief>): Int {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBrief> {
        val fromId = params.key ?: 1
        val loadSize = params.loadSize.coerceIn(1..100)
        return try {
            val result = api.getUsers(fromId, loadSize)
            LoadResult.Page(result, null, fromId+loadSize)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}