package com.example.rbdb.dao

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import com.example.rbdb.model.CardEntity

@Dao
interface CardEntityDao {
    @Query("SELECT * FROM card_entity")
    fun getAllItemEntity(): List<CardEntity>

    @Insert
    fun insert(ItemEntity: CardEntity)

    @Delete
    fun delete(ItemEntity: CardEntity)
}