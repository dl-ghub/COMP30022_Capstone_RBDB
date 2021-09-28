package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface ListEntityDao {
    @Insert
    suspend fun insert(ItemEntity: ListEntity)

    @Delete
    suspend fun delete(ItemEntity: ListEntity)

    @Update
    suspend fun update(ItemEntity: ListEntity)

    @Transaction
    @Query("SELECT * FROM list_entity")
    suspend fun getAllLists(): List<ListEntity>

    // Relational Queries for List <-> Card relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getListWithCards(): List<ListWithCardsEntity>
}