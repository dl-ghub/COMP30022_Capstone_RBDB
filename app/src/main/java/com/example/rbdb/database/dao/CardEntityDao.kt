package com.example.rbdb.database.dao

import androidx.room.*
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
    @Query("SELECT * FROM card_entity ORDER BY name")
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
    @Query("SELECT * FROM card_entity WHERE UPPER(name) = UPPER(:cardName)")
    suspend fun getCardsByName(cardName:String):List<CardEntity>

    //sort cards by name in descending order
    @Query("SELECT * FROM card_entity ORDER BY UPPER(name) ASC")
    suspend fun getAllCardsOrderByName():List<CardEntity>

    //Search for a contact by keywords in their detailed description
    @Query("SELECT * FROM card_entity WHERE description LIKE '%' || :keyword || '%'")
    suspend fun getCardsByKeywordInDescription(keyword:String):List<CardEntity>

    //TODO: update the following query
    /*//Search a card by tag. It uses UPPER() to make the search case insensitive
    @Query("SELECT * FROM card_entity CE INNER JOIN CardTagCrossRef CR ON CE.cardId = CR.cardId INNER JOIN tag_entity TE ON CR.tagId = TE.tagId")
    suspend fun getCardByTag(tagName:String):List<CardEntity>*/


}