package com.bitflyer.testapp.data.userlist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

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
                    Log.e(TAG, "load: ", e)
                    null
                }

                Log.d(TAG, "load users: ${users?.size}")
                if (users.isNullOrEmpty()) loadFromNet(fromId, loadSize)
                else LoadResult.Page(users, null, users.last().id + 1)
            } else {
                dao.clearUsers()
                loadFromNet(fromId, loadSize)
            }
        } catch (e: Exception) {
            Log.e(TAG, "error", e)
            LoadResult.Error(e)
        }
    }

    private suspend fun loadFromNet(fromId: Int, loadSize: Int): LoadResult<Int, UserBriefEntity> {
        //todo uncomment when complete
//        val users = api.getUsers(fromId, loadSize).map(entityMapper::map)
        //todo remove when complete
        delay(1500)
        val users = mutableListOf<UserBriefEntity>().apply {
            repeat(loadSize) {
                this.add(UserBriefEntity(fromId + it, "login${fromId + it}", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRA9nc9vtOqszvTTELelPfWllSPrztOkvD7rg&usqp=CAU"))
            }
        }
        if (Random.nextInt(0, 2) == 0) return LoadResult.Error(Exception())

        Log.d(TAG, "loadFromNet: $fromId, $loadSize, ${users.size}")
        return LoadResult.Page(users, null, fromId + loadSize)
    }
}