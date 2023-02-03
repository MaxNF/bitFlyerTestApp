package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.domain.repository.UserListRepository
import javax.inject.Inject

class ClearUsersUseCase @Inject constructor(private val repo: UserListRepository) {
    suspend operator fun invoke() = repo.clearUsers()
}