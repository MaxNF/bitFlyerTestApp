package com.bitflyer.testapp.domain.usecase

import androidx.paging.PagingData
import com.bitflyer.testapp.BaseTest
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.repository.UserListRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetPagedListUseCaseTest: BaseTest() {

    @MockK
    private lateinit var repo: UserListRepository

    private lateinit var useCase: GetPagedListUseCase

    override fun setUp() {
        super.setUp()
        useCase = GetPagedListUseCase(repo)
    }

    @Test
    fun `useCase returns correct flow`() {
        val flow = flowOf<PagingData<UserBriefEntity>>()
        every { repo.pagedListFlow }.returns(flow)
        val result = useCase.pagedListFlow
        assertThat(result).isEqualTo(flow)
    }
}