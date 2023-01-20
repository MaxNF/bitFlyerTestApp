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
import com.bitflyer.testapp.domain.userlist.UserListRepository
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.domain.userlist.mapper.UserBriefToUserBriefEntityMapper
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository,
    private val githubNetworkApi: GithubNetworkApi,
    private val dao: UserListDao,
    private val entityMapper: UserBriefToUserBriefEntityMapper,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val restoreStateKey: String = "restore"
    }

    val flow = Pager(PagingConfig(50, 20)) {
        UserListPagingSource(githubNetworkApi, dao, entityMapper, state[restoreStateKey] ?: false)
    }.flow.cachedIn(viewModelScope)

    fun onUserClick(user: UserBriefModel) {
        //todo open details
    }
    fun saveData(list: List<UserBriefEntity>) {
        if (list.isNotEmpty()) {
            state[restoreStateKey] = true
            viewModelScope.launch {
                userListRepository.saveUsers(list)
            }
        }
    }
}