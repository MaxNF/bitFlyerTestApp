package com.bitflyer.testapp.ui.compose.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.bitflyer.testapp.R
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.ui.compose.theme.BitflyerTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserListScreen(
    list: Flow<PagingData<UserBriefEntity>>,
    onUserDetailsClick: (String) -> Unit,
) {
    val items = list.collectAsLazyPagingItems()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        ShowInitialLoad(userBriefs = items)
        UserList(
            userBriefs = items,
            onUserDetailsClick = onUserDetailsClick
        )
    }
}

@Composable
fun UserList(
    userBriefs: LazyPagingItems<UserBriefEntity>,
    onUserDetailsClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        showLoadedItems(
            userBriefs = userBriefs,
            onUserDetailsClick = onUserDetailsClick
        )
        when (val state = userBriefs.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingUserListItem()
                }
            }

            is LoadState.Error -> {
                singleItemLoadingError { userBriefs.retry() }
            }

            else -> {}
        }
    }
}

private fun LazyListScope.showLoadedItems(
    userBriefs: LazyPagingItems<UserBriefEntity>,
    onUserDetailsClick: (String) -> Unit
) {
    items(userBriefs.itemCount) {
        val userBrief = userBriefs[it]
        userBrief?.let {
            UserListItem(item = userBrief, onUserDetailsClick)
        }
    }
}

@Composable
private fun BoxScope.ShowInitialLoad(userBriefs: LazyPagingItems<UserBriefEntity>) {
    when (userBriefs.loadState.refresh) {
        is LoadState.Loading -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                repeat(3) {
                    LoadingUserListItem()
                }
            }
        }

        is LoadState.Error -> {
            RefreshErrorState { userBriefs.refresh() }
        }

        else -> {}
    }
}

@Composable
fun BoxScope.RefreshErrorState(
    onReloadClick: () -> Unit,
) {
    val errorMsg = stringResource(id = R.string.user_brief_loading_error)
    val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMsg, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.clickable {
                onReloadClick()
            },
            text = tryAgainMsg,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

fun LazyListScope.singleItemLoadingError(onAppendReloadClick: () -> Unit) {
    item {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            val errorMsg =
                stringResource(id = R.string.user_brief_loading_error)
            val tryAgainMsg =
                stringResource(id = R.string.try_again_button_label)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMsg,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        onAppendReloadClick()
                    },
                    text = tryAgainMsg,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSingleItemLoadingError() {
    LazyColumn(content = {
        singleItemLoadingError {  }
    })
}

@Composable
fun LoadingUserListItem() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .height(96.dp)
    ) {}
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