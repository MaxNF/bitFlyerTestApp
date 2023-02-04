package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.domain.repository.UserListRepository
import javax.inject.Inject

class GetPagedListUseCase @Inject constructor(private val repo: UserListRepository) {
    val pagedListFlow
        get() = repo.pagedListFlow
}