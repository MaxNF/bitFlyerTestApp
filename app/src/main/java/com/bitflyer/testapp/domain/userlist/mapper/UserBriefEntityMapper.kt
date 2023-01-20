package com.bitflyer.testapp.domain.userlist.mapper

import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity
import com.bitflyer.testapp.ui.BaseMapper

class UserBriefEntityMapper : BaseMapper<UserBrief, UserBriefEntity> {

    override fun map(item: UserBrief): UserBriefEntity = UserBriefEntity(item.id, item.login, item.avatarUrl)
}