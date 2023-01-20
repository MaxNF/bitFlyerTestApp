package com.bitflyer.testapp.domain.userlist.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_list")
data class UserBriefEntity(
    @PrimaryKey
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)