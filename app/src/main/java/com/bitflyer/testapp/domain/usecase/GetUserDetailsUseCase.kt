package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repo: UserDetailsRepository) {
    suspend operator fun invoke(login: String) = repo.getUserDetails(login)
}