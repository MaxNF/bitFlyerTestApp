package com.bitflyer.testapp.data.userdetails

import com.bitflyer.testapp.data.BaseRepo
import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.network.GithubNetworkApi
import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.domain.repository.UserDetailsRepository
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(private val api: GithubNetworkApi) : BaseRepo(), UserDetailsRepository {
    override suspend fun getUserDetails(login: String): CallResult<UserDetails> = safeApiCall {
        api.getUserDetails(login)
    }
}