package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface CardEntityDao {
    // Basic queries for Card
    @Insert
    fun insert(ItemEntity: CardEntity)

    @Delete
    fun delete(ItemEntity: CardEntity)

    @Update
    fun update(ItemEntity: CardEntity)

    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getAllCards(): List<CardEntity>

    // Relational Queries for Card <-> Tag relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getCardWithTags(): List<CardWithTagsEntity>

    // Relational Queries for Card <-> List relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getCardWithLists(): List<CardWithListsEntity>
}