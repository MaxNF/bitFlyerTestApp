package com.bitflyer.testapp.ui.userlist

import androidx.lifecycle.SavedStateHandle
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.domain.usecase.GetPagedListUseCase
import com.bitflyer.testapp.domain.usecase.SaveUsersOnProcessDeathUseCase
import com.bitflyer.testapp.userBriefEntityListMock
import io.mockk.coVerify
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
    private lateinit var getPagedListUseCase: GetPagedListUseCase

    @RelaxedMockK
    private lateinit var saveUsersOnProcessDeathUseCase: SaveUsersOnProcessDeathUseCase

    @RelaxedMockK
    private lateinit var state: SavedStateHandle

    private lateinit var viewModel: UserListViewModel

    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserListViewModel(getPagedListUseCase, saveUsersOnProcessDeathUseCase, state)
    }

    @Test
    fun `saveData invoked with empty list, saveUsersOnProcessDeathUseCase is not invoked`() {
        viewModel.saveData(emptyList())
        verify(exactly = 0) { state.set<Boolean>(any(), any()) }
        coVerify(exactly = 0) { saveUsersOnProcessDeathUseCase.invoke(any()) }
    }

    @Test
    fun `saveData invoked with not empty list, saveUsersOnProcessDeathUseCase is invoked`() {
        viewModel.saveData(userBriefEntityListMock)
        verify (exactly = 1) { state.set<Boolean>(any(), any()) }
        coVerify(exactly = 1) { saveUsersOnProcessDeathUseCase.invoke(any()) }
    }
}