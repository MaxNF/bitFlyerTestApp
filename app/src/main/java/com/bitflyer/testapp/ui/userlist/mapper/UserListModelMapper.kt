package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.model.UserListModel
import javax.inject.Inject

class UserListModelMapper @Inject constructor() : BaseMapper<UserBriefEntity, UserListModel> {
    override fun map(item: UserBriefEntity): UserListModel = UserListModel(item.id, item.login, item.avatarUrl)
}