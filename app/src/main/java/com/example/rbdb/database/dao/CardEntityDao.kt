package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface CardEntityDao {
    // Basic queries for Card
    @Insert
    suspend fun insert(ItemEntity: CardEntity)

    @Delete
    suspend fun delete(ItemEntity: CardEntity)

    @Update
    suspend fun update(ItemEntity: CardEntity)

    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getAllCards(): List<CardEntity>

    // Relational Queries for Card <-> Tag relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getCardWithTags(): List<CardWithTagsEntity>

    // Relational Queries for Card <-> List relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getCardWithLists(): List<CardWithListsEntity>
}