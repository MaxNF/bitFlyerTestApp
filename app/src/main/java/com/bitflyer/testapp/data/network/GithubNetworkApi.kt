package com.bitflyer.testapp.data.network

import com.bitflyer.testapp.data.dto.UserBrief
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubNetworkApi {
        companion object {
            fun create(retrofit: Retrofit) = retrofit.create(GithubNetworkApi::class.java)
        }

        @GET("/users")
        suspend fun getUsers(@Query("since") fromId: Int, @Query("per_page") count: Int): Response<List<UserBrief>>
    }