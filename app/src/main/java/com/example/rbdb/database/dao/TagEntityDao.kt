package com.example.rbdb.database.dao

import androidx.room.*
import com.example.rbdb.database.model.*

@Dao
interface TagEntityDao {

    @Insert
    fun insert(ItemEntity: TagEntity)

    @Delete
    fun delete(ItemEntity: TagEntity)

    @Update
    fun update(ItemEntity: TagEntity)

    @Transaction
    @Query("SELECT * FROM tag_entity")
    fun getAllTags(): List<TagEntity>

    // Relational Queries for Tag <-> Card relation
    @Transaction
    @Query("SELECT * FROM tag_entity")
    fun getTagWithCards(): List<TagWithCardsEntity>

    @Query("SELECT tagId FROM tag_entity WHERE tag_entity.name == :nameOfTag")
    fun getTagID(nameOfTag: String): Long
}