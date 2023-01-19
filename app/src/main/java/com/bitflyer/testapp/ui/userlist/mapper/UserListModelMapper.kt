package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.model.UserListModel

class UserListModelMapper : BaseMapper<UserBrief, UserListModel> {
    override fun map(dto: UserBrief): UserListModel = UserListModel(dto.id, dto.login, dto.avatarUrl)
}