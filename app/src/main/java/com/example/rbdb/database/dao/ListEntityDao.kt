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

    @Query("DELETE FROM list_entity WHERE listId = :listId")
    suspend fun deleteByListId(listId: Long)

    @Transaction
    @Query("SELECT * FROM list_entity ORDER BY name")
    suspend fun getAllLists(): List<ListEntity>

    // Relational Queries for List <-> Card relation
    @Transaction
    @Query("SELECT * FROM list_entity")
    suspend fun getListWithCards(): List<ListWithCardsEntity>

    @Transaction
    @Query("SELECT * FROM list_entity WHERE listId = :listId")
    suspend fun getListWithCardsByListId(listId: Long): ListWithCardsEntity
}