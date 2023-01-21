package com.bitflyer.testapp.domain.userdetails

import com.bitflyer.testapp.data.CallResult
import com.bitflyer.testapp.data.userdetails.dto.UserDetails

interface UserDetailsRepository {
    suspend fun getUserDetails(login: String): CallResult<UserDetails>
}