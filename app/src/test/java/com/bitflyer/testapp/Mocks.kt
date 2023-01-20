package com.bitflyer.testapp

import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.entity.UserBriefEntity

val userBriefListMock
    get() = listOf(UserBrief(1, "login", "avatar"))

val userBriefEntityListMock
    get() = listOf(UserBriefEntity(1, "login", "avatar"))