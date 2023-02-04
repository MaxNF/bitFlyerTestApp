package com.bitflyer.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class UserListDao {
    @Query("SELECT * FROM user_list")
    abstract fun getUsers(): List<UserBriefEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<UserBriefEntity>)

    @Query("DELETE FROM user_list")
    abstract fun clearUsers()

    @Transaction
    open fun clearAndSaveUsers(users: List<UserBriefEntity>) {
        clearUsers()
        insertAll(users)
    }
}