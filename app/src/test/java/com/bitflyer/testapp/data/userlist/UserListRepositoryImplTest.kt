package com.bitflyer.testapp.data.userlist

import androidx.paging.Pager
import androidx.paging.PagingData
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.biyflyer.testapp.userBriefEntityListMock
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListRepositoryImplTest : BaseTest() {

    @MockK
    private lateinit var dao: UserListDao

    @MockK
    private lateinit var pager: Pager<Int, UserBriefEntity>

    private lateinit var repo: UserListRepository

    override fun setUp() {
        super.setUp()
        repo = UserListRepositoryImpl(dao, pager)
    }

    @Test
    fun `clearAllAndSave invoked, dao function is also invoked`() {
        runTest(UnconfinedTestDispatcher()) {
            repo.clearAllAndSave(userBriefEntityListMock)
            verify(exactly = 1) { dao.clearAndSaveUsers(any()) }
        }
    }

    @Test
    fun `repo returns correct flow`() {
        val flow = flowOf<PagingData<UserBriefEntity>>()
        every { pager.flow }.returns(flow)
        val result = pager.flow
        assertThat(result).isEqualTo(flow)
    }
}