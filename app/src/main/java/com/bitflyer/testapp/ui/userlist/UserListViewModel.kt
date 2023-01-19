package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.UserListPagingSource
import com.bitflyer.testapp.ui.userlist.mapper.UserListModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val githubNetworkApi: GithubNetworkApi,
    private val mapper: UserListModelMapper
) : ViewModel() {
    val flow = Pager(PagingConfig(50, 20)) {
        UserListPagingSource(githubNetworkApi)
    }.flow.map { data -> data.map { mapper.map(it) } }
        .cachedIn(viewModelScope)
}