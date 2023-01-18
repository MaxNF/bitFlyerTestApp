package com.bitflyer.testapp.domain.userlist

import com.bitflyer.testapp.data.userlist.dto.UserBrief

interface UserListRepository {
    suspend fun getUsers(fromId: Int, count: Int): List<UserBrief>
}