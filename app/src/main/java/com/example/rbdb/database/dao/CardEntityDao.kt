package com.example.rbdb.database.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.rbdb.database.model.*

@Dao
interface CardEntityDao {

    // Basic queries for Card
    @Insert
    suspend fun insert(ItemEntity: CardEntity): Long

    @Delete
    suspend fun delete(ItemEntity: CardEntity)

    @Query("DELETE FROM card_entity WHERE cardId = :cardId")
    suspend fun deleteCardById(cardId: Long)

    @Update
    suspend fun update(ItemEntity: CardEntity)

    @Query("SELECT * FROM card_entity WHERE cardId = :cardId")
    suspend fun getCardById(cardId: Long): CardEntity

    @Transaction
    @Query("SELECT * FROM card_entity ORDER BY firstName")
    suspend fun getAllCards(): List<CardEntity>

    // Relational Queries for Card <-> Tag relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getCardWithTags(): List<CardWithTagsEntity>

    // Relational Queries for Card <-> List relation
    @Transaction
    @Query("SELECT * FROM card_entity")
    suspend fun getCardWithLists(): List<CardWithListsEntity>

    //Search a card by name. It uses UPPER() to make the search case insensitive
    @Query("SELECT * FROM card_entity WHERE UPPER(firstName) LIKE '%' || UPPER(:cardName) || '%' ORDER BY length(firstName)")
    suspend fun getCardsByName(cardName:String):List<CardEntity>

    //sort cards by name in descending order
    @Query("SELECT * FROM card_entity ORDER BY UPPER(firstName) ASC")
    suspend fun getAllCardsOrderByName():List<CardEntity>

    //Search for a contact by keywords in their detailed description
    @Query("SELECT * FROM card_entity WHERE description LIKE '%' || :keyword || '%'")
    suspend fun getCardsByKeywordInDescription(keyword:String):List<CardEntity>

    //Search a card by tags. The relationship between tags is "OR"
    @RawQuery
    suspend fun getCardByTagIds(query: SupportSQLiteQuery):List<CardEntity>

    //Search a card by keyword in selected columns. It currently only support single keyword as the keyword.
    // The columns are selected by the user.
    @RawQuery
    suspend fun getCardByKeywordInSelectedColumns(query: SupportSQLiteQuery):List<CardEntity>

}