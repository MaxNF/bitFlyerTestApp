package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.UserListRepository
import com.bitflyer.testapp.ui.userlist.mapper.UserListModelMapper
import com.bitflyer.testapp.ui.userlist.model.UserListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository,
    private val mapper: UserListModelMapper,
    pager: Pager<Int, UserBrief>
) : ViewModel() {
    val flow = pager
        .flow.map { data -> data.map { mapper.map(it) } }
        .cachedIn(viewModelScope)

    fun onUserClick(user: UserListModel) {
        //todo open details
    }
}