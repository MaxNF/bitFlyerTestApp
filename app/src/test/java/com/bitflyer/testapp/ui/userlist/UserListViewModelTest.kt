package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.SavedStateHandle
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.local.UserListDao
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.usecase.ClearUsersUseCase
import com.bitflyer.testapp.domain.usecase.SaveUsersUseCase
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.userBriefEntityListMock
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest : BaseTest() {

    @RelaxedMockK
    private lateinit var clearUsersUseCase: ClearUsersUseCase

    @RelaxedMockK
    private lateinit var saveUsersUseCase: SaveUsersUseCase

    @MockK
    private lateinit var githubNetworkApi: GithubNetworkApi

    @MockK
    private lateinit var dao: UserListDao

    @MockK
    private lateinit var entityMapper: BaseMapper<UserBrief, UserBriefEntity>

    @RelaxedMockK
    private lateinit var state: SavedStateHandle

    private lateinit var viewModel: UserListViewModel

    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserListViewModel(clearUsersUseCase, saveUsersUseCase, githubNetworkApi, dao, entityMapper, state)
    }

    @Test
    fun `saveData invoked with empty list, saveUsers, clearUsers and set functions are not invoked`() {
        viewModel.saveData(emptyList())
        verify(exactly = 0) { state.set<Boolean>(any(), any()) }
        coVerify(exactly = 0) { saveUsersUseCase.invoke(any()) }
        coVerify(exactly = 0) { clearUsersUseCase.invoke() }
    }

    @Test
    fun `saveData invoked with not empty list, saveUsers, clearUsers and set functions are invoked`() {
        viewModel.saveData(userBriefEntityListMock)
        verify (exactly = 1) { state.set<Boolean>(any(), any()) }
        coVerify(exactly = 1) { saveUsersUseCase.invoke(any()) }
        coVerify(exactly = 1) { clearUsersUseCase.invoke() }
    }
}