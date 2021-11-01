package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface ListEntityDao {
    @Query("SELECT * FROM list_entity WHERE listId = :listId")
    suspend fun getListById(listId: Long): ListEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ItemEntity: ListEntity):Long

    @Delete
    suspend fun delete(ItemEntity: ListEntity)

    @Update
    suspend fun update(ItemEntity: ListEntity)

    /* Update list name by id */
    @Query("UPDATE list_entity SET name = :name WHERE listId = :listId")
    suspend fun updateListName(name: String, listId: Long)

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