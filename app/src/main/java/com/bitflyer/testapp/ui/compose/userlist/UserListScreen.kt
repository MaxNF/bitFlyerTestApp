package com.bitflyer.testapp.ui.compose.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.ui.compose.theme.BitflyerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserListScreen(
    list: Flow<PagingData<UserBriefEntity>>,
    onUserDetailsClick: (String) -> Unit,
) {
    val items = list.collectAsLazyPagingItems()
    UserList(userBriefs = items, onUserDetailsClick = onUserDetailsClick)
}

@Composable
fun UserList(
    userBriefs: LazyPagingItems<UserBriefEntity>,
    onUserDetailsClick: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(userBriefs.itemCount) {
            val userBrief = userBriefs[it]
            userBrief?.let {
                UserListItem(item = userBrief, onUserDetailsClick)
            }
        }
    }
}

@Composable
fun UserListItem(item: UserBriefEntity, onClick: (String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item.login) },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = item.login,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserListItem() {
    BitflyerTheme {
        UserListItem(item = UserBriefEntity(1, "MaxNF", "")) {}
    }
}

@Preview
@Composable
fun UserListScreenPreview() {
    UserListScreen(list = flowOf(), onUserDetailsClick = {})
}