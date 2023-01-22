package com.bitflyer.testapp.data.network

import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubNetworkApi {
        companion object {
            fun create(retrofit: Retrofit): GithubNetworkApi = retrofit.create(GithubNetworkApi::class.java)
        }

        @GET("/users")
        suspend fun getUsers(@Query("since") fromId: Int, @Query("per_page") count: Int): List<UserBrief>

        @GET("/users/{login}")
        suspend fun getUserDetails(@Path("login") login: String): UserDetails
    }