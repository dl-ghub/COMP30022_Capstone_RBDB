package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface CardEntityDao {
    // ADD QUERIES HERE, MISSING SOME BASIC ONES

    // Basic queries for User
    @Query("SELECT * FROM user_entity")
    fun getAllUserEntity(): List<UserEntity>

    // Basic queries for Card
    @Query("SELECT * FROM card_entity")
    fun getAllItemEntity(): List<CardEntity>

    @Insert
    fun insert(ItemEntity: CardEntity)

    @Delete
    fun delete(ItemEntity: CardEntity)

    // Relational Queries for Card <-> Tag relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getCardWithTags(): List<CardWithTagsEntity>

    @Transaction
    @Query("SELECT * FROM tag_entity")
    fun getTagWithCards(): List<TagWithCardsEntity>

    // Relational Queries for Card <-> List relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    fun getCardWithLists(): List<CardWithListsEntity>

    @Transaction
    @Query("SELECT * FROM list_entity")
    fun getListWithCards(): List<ListWithCardsEntity>
}