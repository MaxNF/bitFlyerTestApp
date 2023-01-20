package com.bitflyer.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bitflyer.testapp.domain.entity.UserBriefEntity

@Dao
interface UserListDao {
    @Query("SELECT * FROM user_list")
    fun getUsers(): List<UserBriefEntity>

    @Insert
    fun insertAll(users: List<UserBriefEntity>)

    @Query("DELETE FROM user_list")
    fun clearUsers()
}