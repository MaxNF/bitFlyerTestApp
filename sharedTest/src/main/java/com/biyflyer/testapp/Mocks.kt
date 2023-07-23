package com.biyflyer.testapp

import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.data.local.UserBriefEntity
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel

val userBriefMock
    get() = UserBrief(1, "login", "avatar")

val userBriefListMock
    get() = listOf(userBriefMock)

val userBriefEntityMock
    get() = UserBriefEntity(1, "login", "avatar")

val userBriefEntityListMock
    get() = listOf(userBriefEntityMock)

val userDetailsMock
    get() = UserDetails(
        "login",
        "avatarUrl",
        "name",
        "company",
        "blogUrl",
        "location",
        "email",
        "twitter",
        1,
        2,
        3
    )

val userDetailsModelMock
    get() = UserDetailsModel(
        "login",
        "avatarUrl",
        "name",
        "company",
        "blogUrl",
        "location",
        "email",
        "twitter",
        "1",
        "2",
        "3"
    )