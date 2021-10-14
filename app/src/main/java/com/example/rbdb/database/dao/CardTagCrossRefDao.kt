package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.CardListCrossRef
import com.example.rbdb.database.model.CardTagCrossRef


@Dao
interface CardTagCrossRefDao {

    @Insert
    suspend fun insert(cardTagCrossRef: CardTagCrossRef)

    @Delete
    suspend fun delete(cardTagCrossRef: CardTagCrossRef)

    @Query("DELETE FROM card_tag_cross_ref WHERE cardId = :cardId")
    suspend fun deleteByCardId(cardId: Long)

    @Update
    suspend fun update(cardTagCrossRef: CardTagCrossRef)

    @Query("SELECT * FROM card_tag_cross_ref")
    suspend fun getAllCardTagCrossRef(): List<CardTagCrossRef>

}