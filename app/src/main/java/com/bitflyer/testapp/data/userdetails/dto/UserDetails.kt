package com.bitflyer.testapp.data.userdetails.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String? = null,
    val company: String? = null,
    @SerialName("blog")
    val blogUrl: String? = null,
    val location: String? = null,
    val email: String? = null,
    @SerialName("twitter_username")
    val twitter: String? = null,
    val followers: Int,
    val following: Int,
    @SerialName("public_repos")
    val repos: Int
)
