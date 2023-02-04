package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.usecase.GetPagedListUseCase
import com.bitflyer.testapp.domain.usecase.SaveUsersOnProcessDeathUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    getPagedListUseCase: GetPagedListUseCase,
    private val saveUsersOnProcessDeathUseCase: SaveUsersOnProcessDeathUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        const val RESTORE_STATE_KEY: String = "restore"
    }
    val flow = getPagedListUseCase.pagedListFlow.cachedIn(viewModelScope)

    fun saveData(users: List<UserBriefEntity>) {
        if (users.isNotEmpty()) {
            state[RESTORE_STATE_KEY] = true
            viewModelScope.launch {
                saveUsersOnProcessDeathUseCase(users)
            }
        }
    }
}