package com.bitflyer.testapp.data.userlist

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.domain.userlist.mapper.UserBriefToUserBriefEntityMapper
import com.bitflyer.testapp.userBriefEntityListMock
import com.bitflyer.testapp.userBriefEntityMock
import com.bitflyer.testapp.userBriefListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
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

    @MockK
    private lateinit var mapper: UserBriefToUserBriefEntityMapper

    @RelaxedMockK
    private lateinit var dao: UserListDao

    private lateinit var pagingSource: UserListPagingSource

    private lateinit var pagingConfig: PagingConfig

    override fun setUp() {
        super.setUp()
        every { mapper.map(any()) }.returns(userBriefEntityMock)
        pagingConfig = PagingConfig(pageSize)
    }

    @Test
    fun `getRefreshKey return 1`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, false)
        val result = pagingSource.getRefreshKey(PagingState(listOf(), null, pagingConfig, 0))
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Restore state is false, Api call finishes without exception, result is Page`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, false)
        coEvery { networkApi.getUsers(any(), any()) }.returns(userBriefListMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(userBriefEntityListMock)
        }
    }

    @Test
    fun `Restore state is false, Api throws IOException, result is Error`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, false)
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
    fun `Restore state is false, Api throws HttpException, result is Error`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, false)
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

    @Test
    fun `Restore state is true, dao call is ok, only dao function is invoked`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, true)
        every { dao.getUsers() }.returns(userBriefEntityListMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            pagingSource.load(params)
            verify(exactly = 1) { dao.getUsers() }
            coVerify(exactly = 0) { networkApi.getUsers(any(),any()) }
        }
    }

    @Test
    fun `Restore state is true, dao call throws exception, both dao and api functions are invoked`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, true)
        every { dao.getUsers() }.throws(IOException())
        coEvery { networkApi.getUsers(any(), any()) }.returns(userBriefListMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            pagingSource.load(params)
            verify(exactly = 1) { dao.getUsers() }
            coVerify(exactly = 1) { networkApi.getUsers(any(),any()) }
        }
    }

    @Test
    fun `Restore state is true, dao call returns empty list, both dao and api functions are invoked`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, true)
        coEvery { networkApi.getUsers(any(), any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            pagingSource.load(params)
            verify(exactly = 1) { dao.getUsers() }
            coVerify(exactly = 1) { networkApi.getUsers(any(),any()) }
        }
    }
    @Test
    fun `Restore state is true, Dao throws IOException but api call is Ok, result is Page`() {
        pagingSource = UserListPagingSource(networkApi, dao, mapper, true)
        every { dao.getUsers() }.throws(IOException())
        coEvery { networkApi.getUsers(any(), any()) }.returns(userBriefListMock)
        runTest(UnconfinedTestDispatcher()) {
            val params = PagingSource.LoadParams.Append(1, pageSize, false)
            val result = pagingSource.load(params)
            assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            assertThat((result as PagingSource.LoadResult.Page).data).isEqualTo(userBriefEntityListMock)
        }
    }
}