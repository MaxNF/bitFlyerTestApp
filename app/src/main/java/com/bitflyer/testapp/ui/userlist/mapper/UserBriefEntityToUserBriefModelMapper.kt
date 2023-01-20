package com.bitflyer.testapp.ui.userlist.mapper

import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userlist.model.UserBriefModel
import javax.inject.Inject

class UserBriefEntityToUserBriefModelMapper @Inject constructor() : BaseMapper<UserBriefEntity, UserBriefModel> {
    override fun map(item: UserBriefEntity): UserBriefModel = UserBriefModel(item.id, item.login, item.avatarUrl)
}