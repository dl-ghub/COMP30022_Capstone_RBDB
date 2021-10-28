package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface TagEntityDao {

    @Insert
    suspend fun insert(ItemEntity: TagEntity)

    @Delete
    suspend fun delete(ItemEntity: TagEntity)

    @Query("DELETE FROM tag_entity WHERE tagId = :tagId")
    suspend fun deleteByTagId(tagId: Long)

    @Update
    suspend fun update(ItemEntity: TagEntity)

    @Transaction
    @Query("SELECT * FROM tag_entity")
    suspend fun getAllTags(): List<TagEntity>

    // Relational Queries for Tag <-> Card relation
    @Transaction
    @Query("SELECT * FROM tag_entity")
    suspend fun getTagWithCards(): List<TagWithCardsEntity>

    @Query("SELECT tagId FROM tag_entity WHERE tag_entity.name == :nameOfTag")
    suspend fun getTagID(nameOfTag: String): Long
}