package com.bitflyer.testapp.domain.usecase

import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.domain.repository.UserListRepository
import javax.inject.Inject

class SaveUsersUseCase @Inject constructor(private val repo: UserListRepository) {
    suspend operator fun invoke(userList: List<UserBriefEntity>) = repo.saveUsers(userList)
}