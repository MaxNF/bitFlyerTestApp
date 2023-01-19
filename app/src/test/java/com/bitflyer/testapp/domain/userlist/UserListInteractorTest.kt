package com.bitflyer.testapp.domain.userlist

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.userBriefListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListInteractorTest : BaseTest() {

    @MockK
    private lateinit var repo: UserListRepository

    private lateinit var interactor: UserListInteractor

    override fun setUp() {
        super.setUp()
        interactor = UserListInteractor(repo)
    }

    @Test
    fun `Repo returns Success, interactor also returns Success`() {
        val mock = CallResult.Success(userBriefListMock)
        coEvery { repo.getUsers(any(), any()) }.returns(mock)
        runTest(UnconfinedTestDispatcher()) {
            assertThat(interactor.getUsers(1, 1)).isEqualTo(mock)
        }
    }


    @Test
    fun `Repo returns NetworkError, interactor also returns NetworkError`() {
        val mock = CallResult.NetworkError
        coEvery { repo.getUsers(any(), any()) }.returns(mock)
        runTest(UnconfinedTestDispatcher()) {
            assertThat(interactor.getUsers(1, 1)).isEqualTo(mock)
        }
    }

    @Test
    fun `Repo returns HttpException, interactor also returns NetworkError`() {
        val mock = CallResult.HttpError(400, "")
        coEvery { repo.getUsers(any(), any()) }.returns(mock)
        runTest(UnconfinedTestDispatcher()) {
            assertThat(interactor.getUsers(1, 1)).isEqualTo(mock)
        }
    }
}