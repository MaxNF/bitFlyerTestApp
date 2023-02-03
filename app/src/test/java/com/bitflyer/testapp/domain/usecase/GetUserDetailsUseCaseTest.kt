package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.bitflyer.testapp.userDetailsMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserDetailsUseCaseTest : BaseTest() {

    @MockK
    private lateinit var repo: UserDetailsRepository

    private lateinit var useCase: GetUserDetailsUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetUserDetailsUseCase(repo)
    }

    @Test
    fun `Repo call returns success, use case also returns Success`() {
        coEvery { repo.getUserDetails(any()) }.returns(CallResult.Success(userDetailsMock))
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase("")
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(userDetailsMock)
        }
    }

    @Test
    fun `Repo call returns IOError, use case also returns IOError`() {
        coEvery { repo.getUserDetails(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase("")
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo call returns HttpException, use case also returns HttpError`() {
        coEvery { repo.getUserDetails(any()) }.returns(CallResult.HttpError(400, "msg"))
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase("")
            assertThat(result).isInstanceOf(CallResult.HttpError::class.java)
            assertThat((result as CallResult.HttpError).code).isEqualTo(400)
            assertThat(result.message).isEqualTo("msg")
        }
    }

    @Test
    fun `Repo call returns UnknownError, use case also returns UnknownError`() {
        coEvery { repo.getUserDetails(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase("")
            assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }
}