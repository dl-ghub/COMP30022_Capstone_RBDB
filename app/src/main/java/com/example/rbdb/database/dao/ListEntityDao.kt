package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface ListEntityDao {
    @Insert
    fun insert(ItemEntity: ListEntity)

    @Delete
    fun delete(ItemEntity: ListEntity)

    @Update
    fun update(ItemEntity: ListEntity)

    @Transaction
    @Query("SELECT * FROM list_entity")
    fun getListWithCards(): List<ListWithCardsEntity>

    // Relational Queries for List <-> Card relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getTagWithCards(): List<ListWithCardsEntity>
}