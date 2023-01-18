package com.bitflyer.testapp.domain.userlist

import com.bitflyer.testapp.data.dto.UserBrief

interface UserListRepository {
    fun getUsers(): UserBrief
}