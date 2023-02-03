package com.bitflyer.testapp.data.local

import com.bitflyer.testapp.data.userlist.dto.UserBrief
import com.bitflyer.testapp.ui.BaseMapper
import javax.inject.Inject

class UserBriefToUserBriefEntityMapper @Inject constructor() : BaseMapper<UserBrief, UserBriefEntity> {
    override fun map(item: UserBrief): UserBriefEntity = UserBriefEntity(item.id, item.login, item.avatarUrl)
}