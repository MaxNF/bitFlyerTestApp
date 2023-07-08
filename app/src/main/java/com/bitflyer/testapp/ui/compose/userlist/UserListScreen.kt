package com.bitflyer.testapp.ui.compose.userlist

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import com.bitflyer.testapp.data.local.UserBriefEntity
import kotlinx.coroutines.flow.Flow

@Composable
fun UserListScreen(
    list: Flow<PagingData<UserBriefEntity>>,
    onUserDetailsClick: (String) -> Unit,
) {

}