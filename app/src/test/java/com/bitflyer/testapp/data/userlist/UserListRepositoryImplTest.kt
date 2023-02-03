package com.bitflyer.testapp.data.userlist

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.bitflyer.testapp.userBriefEntityListMock
import com.bitflyer.testapp.userBriefListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class UserListRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var networkApi: GithubNetworkApi

    @MockK
    private lateinit var dao: UserListDao

    private lateinit var repo: UserListRepository

    override fun setUp() {
        super.setUp()
        repo = UserListRepositoryImpl(networkApi, dao)
    }

    @Test
    fun `Api call finishes without exception, result is Success`() {
        coEvery { networkApi.getUsers(any(), any()) }.returns(userBriefListMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUsers(1, 1)
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(userBriefListMock)
        }
    }

    @Test
    fun `Api throws IOException, result is IOError`() {
        coEvery { networkApi.getUsers(any(), any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUsers(1, 1)
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Api throws HttpException, result is HttpError`() {
        coEvery { networkApi.getUsers(any(), any()) }.throws(HttpException(Response.error<Nothing>(400, "".toResponseBody())))
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUsers(1, 1)
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun `Dao call finishes without exception, result is Success`() {
        coEvery { dao.getUsers() }.returns(userBriefEntityListMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCachedUsers()
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(userBriefEntityListMock)
        }
    }

    @Test
    fun `Dao throws IOException, result is IOError`() {
        coEvery { dao.getUsers() }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getCachedUsers()
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `saveUsers invoked, dao function is also invoked`() {
        runTest(UnconfinedTestDispatcher()) {
            repo.saveUsers(userBriefEntityListMock)
            verify(exactly = 1) { dao.insertAll(any()) }
        }
    }

    @Test
    fun `clearUsers invoked, dao function is also invoked`() {
        runTest(UnconfinedTestDispatcher()) {
            repo.clearUsers()
            verify(exactly = 1) { dao.clearUsers() }
        }
    }
}