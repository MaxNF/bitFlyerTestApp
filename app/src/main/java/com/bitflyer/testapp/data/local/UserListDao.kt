package com.bitflyer.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bitflyer.testapp.domain.userlist.entity.UserBriefEntity

@Dao
interface UserListDao {
    @Query("SELECT * FROM user_list")
    fun getUsers(): List<UserBriefEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserBriefEntity>)

    @Query("DELETE FROM user_list")
    fun clearUsers()
}