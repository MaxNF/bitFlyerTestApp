package com.bitflyer.testapp.data.userlist

import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.bitflyer.testapp.userBriefEntityListMock
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var dao: UserListDao

    private lateinit var repo: UserListRepository

    override fun setUp() {
        super.setUp()
        repo = UserListRepositoryImpl(dao)
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