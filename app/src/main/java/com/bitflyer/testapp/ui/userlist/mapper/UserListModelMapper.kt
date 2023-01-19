package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.model.UserListModel
import javax.inject.Inject

class UserListModelMapper @Inject constructor(): BaseMapper<UserBrief, UserListModel> {
    override fun map(dto: UserBrief): UserListModel = UserListModel(dto.id, dto.login, dto.avatarUrl)
}