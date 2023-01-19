package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.userBriefListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val pageSize = 50

@OptIn(ExperimentalCoroutinesApi::class)
class UserListPagingSourceTest : BaseTest() {

    @MockK
    private lateinit var networkApi: GithubNetworkApi

    lateinit var pagingSource: UserListPagingSource

    lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        pagingSource = UserListPagingSource(networkApi)
        pagingConfig = PagingConfig(pageSize)
    }

    @Test
    fun `getRefreshKey return 1`() {
        val result = pagingSource.getRefreshKey(PagingState(listOf(), null, pagingConfig, 0))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Api call finishes without exception, result is Page`() {
        coEvery { networkApi.getUsers(any(), any()) }.returns(userBriefListMock)
        runTest(UnconfinedTestDispatcher()) {
        val params = PagingSource.LoadParams.Append(1, pageSize, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(userBriefListMock)
        }
    }

    @Test
    fun `Api throws IOException, result is Error`() {
        coEvery { networkApi.getUsers(any(), any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                IOException::class.java
            )
        }
    }

    @Test
    fun `Api throws HttpException, result is Error`() {
        coEvery { networkApi.getUsers(any(), any()) }.throws(HttpException(Response.error<Nothing>(400, "".toResponseBody())))
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
            assertThat((result as PagingSource.LoadResult.Error).throwable).isInstanceOf(
                HttpException::class.java
            )
        }
    }
}