package com.bitflyer.testapp.ui.compose.userlist

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.bitflyer.testapp.data.local.UserBriefEntity
import kotlinx.coroutines.flow.Flow

@Composable
fun UserListScreen(
    list: Flow<PagingData<UserBriefEntity>>,
    onUserDetailsClick: (String) -> Unit,
) {
    Button(onClick = { onUserDetailsClick("MaxNF") }, modifier = Modifier.size(100.dp)) {
        Text(text = "TEST")
    }
}