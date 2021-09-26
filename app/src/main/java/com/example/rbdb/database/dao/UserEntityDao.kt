package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface UserEntityDao {
    @Insert
    fun insert(ItemEntity: UserEntity)

    @Delete
    fun delete(ItemEntity: UserEntity)

    @Update
    fun update(ItemEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM user_entity")
    fun getAllUsers(): List<UserEntity>
}