package com.bitflyer.testapp.data.userlist.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBrief(
    val id: Int,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)