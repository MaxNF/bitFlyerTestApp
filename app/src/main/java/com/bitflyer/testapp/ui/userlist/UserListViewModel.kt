package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.UserListPagingSource
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.usecase.ClearUsersUseCase
import com.bitflyer.testapp.domain.usecase.SaveUsersUseCase
import com.bitflyer.testapp.ui.BaseMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val clearUsersUseCase: ClearUsersUseCase,
    private val saveUsersUseCase: SaveUsersUseCase,
    githubNetworkApi: GithubNetworkApi,
    dao: UserListDao,
    entityMapper: BaseMapper<UserBrief, UserBriefEntity>,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val RESTORE_STATE_KEY: String = "restore"
    }

    val flow = Pager(PagingConfig(pageSize = 50, prefetchDistance = 20, initialLoadSize = 50, enablePlaceholders = true)) {
        UserListPagingSource(githubNetworkApi, dao, entityMapper, state[RESTORE_STATE_KEY] ?: false)
    }.flow.cachedIn(viewModelScope)

    fun saveData(list: List<UserBriefEntity>) {
        if (list.isNotEmpty()) {
            state[RESTORE_STATE_KEY] = true
            viewModelScope.launch {
                clearUsersUseCase()
                saveUsersUseCase(list)
            }
        }
    }
}