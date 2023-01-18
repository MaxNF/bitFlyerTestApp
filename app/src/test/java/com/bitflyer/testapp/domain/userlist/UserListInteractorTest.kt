package com.bitflyer.testapp.domain.userlist

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.userBriefMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListInteractorTest: BaseTest() {

    @MockK
    private lateinit var repo: UserListRepository

    private lateinit var interactor: UserListInteractor

    @Before
    override fun setUp() {
        super.setUp()
        interactor = UserListInteractor(repo)
    }

    @Test
    fun `GetUser is successful`() {
        val mock = userBriefMock
        coEvery { repo.getUsers() }.returns(userBriefMock)
        runTest(UnconfinedTestDispatcher()) {
            assertThat(interactor.getUsers()).isEqualTo(mock)
        }
    }

    @Test(expected = Exception::class)
    fun `GetUser throws an exception`() {
        coEvery { repo.getUsers() }.throws(Exception())
        runTest(UnconfinedTestDispatcher()) {
            interactor.getUsers()
        }
    }
}