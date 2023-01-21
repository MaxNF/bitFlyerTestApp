package com.bitflyer.testapp.data.userdetails.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String,
    val company: String,
    val blogUrl: String,
    val location: String,
    val email: String,
    @SerialName("twitter_username")
    val twitter: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    @SerialName("public_repos")
    val repos: Int
)
