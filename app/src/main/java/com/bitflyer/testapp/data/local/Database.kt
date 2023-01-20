package com.bitflyer.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bitflyer.testapp.domain.entity.UserBriefEntity

@Database(entities = [UserBriefEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userListDao(): UserListDao
}