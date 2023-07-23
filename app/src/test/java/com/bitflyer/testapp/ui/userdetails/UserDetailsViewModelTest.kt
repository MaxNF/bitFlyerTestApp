package com.bitflyer.testapp.ui.userdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.domain.usecase.GetUserDetailsUseCase
import com.bitflyer.testapp.ui.userdetails.mapper.UserDetailsToUserDetailsModelMapper
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import com.bitflyer.testapp.ui.userdetails.state.UserDetailsScreenState
import com.biyflyer.testapp.userDetailsMock
import com.biyflyer.testapp.userDetailsModelMock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailsViewModelTest : BaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetUserDetailsUseCase

    @MockK
    private lateinit var mapper: UserDetailsToUserDetailsModelMapper

    @RelaxedMockK
    private lateinit var stateHandle: SavedStateHandle

    @RelaxedMockK
    private lateinit var observer: Observer<UserDetailsScreenState>

    private val arguments = mutableListOf<UserDetailsScreenState>()

    private lateinit var viewModel: UserDetailsViewModel

    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserDetailsViewModel(useCase, mapper, stateHandle)
        viewModel.screenState.observeForever(observer)
        every { observer.onChanged(capture(arguments)) } answers { }
    }

    @Test
    fun `savedStateHandle is not null, Loaded event is received`() {
        every { stateHandle.get<UserDetailsModel>(any()) }.returns(
            userDetailsModelMock
        )
        viewModel.fetchDetails("")
        assertThat(arguments[0]).isInstanceOf(UserDetailsScreenState.Loaded::class.java)
        assertThat((arguments[0] as UserDetailsScreenState.Loaded).model).isEqualTo(
            userDetailsModelMock
        )
    }

    @Test
    fun `savedStateHandle is null and repo call is successful, Loading and Loaded events are received`() {
        every { stateHandle.get<UserDetailsModel>(any()) }.returns(null)
        coEvery { useCase.invoke(any()) }.returns(CallResult.Success(
            userDetailsMock
        ))
        every { mapper.map(any()) }.returns(userDetailsModelMock)
        viewModel.fetchDetails("")
        assertThat(arguments[0]).isInstanceOf(UserDetailsScreenState.Loading::class.java)
        assertThat((arguments[1] as UserDetailsScreenState.Loaded).model).isEqualTo(
            userDetailsModelMock
        )
    }

    @Test
    fun `savedStateHandle is null and repo call is not successful, Loading and Error events are received`() {
        every { stateHandle.get<UserDetailsModel>(any()) }.returns(null)
        coEvery { useCase.invoke(any()) }.returns(CallResult.IOError)
        viewModel.fetchDetails("")
        assertThat(arguments[0]).isInstanceOf(UserDetailsScreenState.Loading::class.java)
        assertThat((arguments[1])).isInstanceOf(UserDetailsScreenState.Error::class.java)
    }
}