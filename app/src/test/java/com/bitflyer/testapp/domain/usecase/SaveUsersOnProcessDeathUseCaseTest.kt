package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.bitflyer.testapp.userBriefEntityListMock
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUsersOnProcessDeathUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repo: UserListRepository

    private lateinit var useCase: SaveUsersOnProcessDeathUseCase

    override fun setUp() {
        super.setUp()
        useCase = SaveUsersOnProcessDeathUseCase(repo)
    }

    @Test
    fun `useCase is invoked, repos function clearAllAndSave is also invoked`() {
        runTest(UnconfinedTestDispatcher()) {
            useCase.invoke(userBriefEntityListMock)
        }
        coVerify(exactly = 1) { repo.clearAllAndSave(any()) }
    }
}