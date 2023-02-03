package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.bitflyer.testapp.userBriefEntityListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUsersUseCaseTest: BaseTest() {

    @MockK
    private lateinit var repo: UserListRepository

    private lateinit var useCase: SaveUsersUseCase

    override fun setUp() {
        super.setUp()
        useCase = SaveUsersUseCase(repo)
    }

    @Test
    fun `Repo call returns success, use case also returns Success`() {
        coEvery { repo.saveUsers(any()) }.returns(CallResult.Success(Unit))
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase(userBriefEntityListMock)
            assertThat(result).isInstanceOf(CallResult.Success::class.java)
            assertThat((result as CallResult.Success).value).isEqualTo(Unit)
        }
    }

    @Test
    fun `Repo call returns IOError, use case also returns IOError`() {
        coEvery { repo.saveUsers(any()) }.returns(CallResult.IOError)
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase(userBriefEntityListMock)
            assertThat(result).isInstanceOf(CallResult.IOError::class.java)
        }
    }

    @Test
    fun `Repo call returns UnknownError, use case also returns UnknownError`() {
        coEvery { repo.saveUsers(any()) }.returns(CallResult.UnknownError)
        runTest(UnconfinedTestDispatcher()) {
            val result = useCase(userBriefEntityListMock)
            assertThat(result).isInstanceOf(CallResult.UnknownError::class.java)
        }
    }
}