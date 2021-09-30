package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.CardListCrossRef

@Dao
interface CardListCrossRefDao {

    @Insert
    suspend fun insert(cardListCrossRef: CardListCrossRef)

    @Delete
    suspend fun delete(cardListCrossRef: CardListCrossRef)

    @Update
    suspend fun update(cardListCrossRef: CardListCrossRef)

    @Query("SELECT * FROM card_list_cross_ref")
    suspend fun getAllCardListCrossRef(): List<CardListCrossRef>


}