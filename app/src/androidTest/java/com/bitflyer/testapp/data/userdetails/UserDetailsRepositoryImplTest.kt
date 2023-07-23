package com.bitflyer.testapp.data.userdetails

import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import com.biyflyer.testapp.userDetailsMock
import javax.inject.Inject

class UserDetailsRepositoryImplTest @Inject constructor(): UserDetailsRepository {
    override suspend fun getUserDetails(login: String): CallResult<UserDetails> {
        return CallResult.Success(userDetailsMock)
    }
}