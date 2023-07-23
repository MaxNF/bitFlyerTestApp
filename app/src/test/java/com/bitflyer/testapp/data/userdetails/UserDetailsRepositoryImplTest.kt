package com.bitflyer.testapp.data.userdetails

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.biyflyer.testapp.userDetailsMock
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

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailsRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var networkApi: GithubNetworkApi

    private lateinit var repo: UserDetailsRepository

    override fun setUp() {
        super.setUp()
        repo = UserDetailsRepositoryImpl(networkApi)
    }

    @Test
    fun `Api call finishes without exception, result is Success`() {
        coEvery { networkApi.getUserDetails(any()) }.returns(userDetailsMock)
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUserDetails("")
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(
                userDetailsMock
            )
        }
    }

    @Test
    fun `Api throws IOException, result is IOError`() {
        coEvery { networkApi.getUserDetails(any()) }.throws(IOException())
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUserDetails("")
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Api throws HttpException, result is HttpError`() {
        coEvery { networkApi.getUserDetails(any()) }.throws(HttpException(Response.error<Nothing>(400, "".toResponseBody())))
        runTest(UnconfinedTestDispatcher()) {
            val result = repo.getUserDetails("")
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
        }
    }
}