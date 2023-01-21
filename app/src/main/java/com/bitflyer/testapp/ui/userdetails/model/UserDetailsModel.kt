package com.bitflyer.testapp.ui.userdetails.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailsModel(
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val blogUrl: String?,
    val location: String?,
    val email: String?,
    val twitter: String?,
    val followers: String,
    val following: String,
    val repos: String
): Parcelable