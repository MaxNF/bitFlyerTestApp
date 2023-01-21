package com.bitflyer.testapp.ui.userdetails.mapper

import com.bitflyer.testapp.data.userdetails.dto.UserDetails
import com.bitflyer.testapp.ui.BaseMapper
import com.bitflyer.testapp.ui.userdetails.model.UserDetailsModel
import javax.inject.Inject

class UserDetailsToUserDetailsModelMapper @Inject constructor() : BaseMapper<UserDetails, UserDetailsModel> {
    override fun map(item: UserDetails): UserDetailsModel = UserDetailsModel(
        item.login,
        item.avatarUrl,
        item.name,
        item.company,
        item.blogUrl,
        item.location,
        item.email,
        item.twitter,
        item.followers.toString(),
        item.following.toString(),
        item.repos.toString()
    )
}