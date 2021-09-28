package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface UserEntityDao {
    @Insert
    suspend fun insert(ItemEntity: UserEntity)

    @Delete
    suspend fun delete(ItemEntity: UserEntity)

    @Update
    suspend fun update(ItemEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM user_entity")
    suspend fun getAllUsers(): List<UserEntity>
}