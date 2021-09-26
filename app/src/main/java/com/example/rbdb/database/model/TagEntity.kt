package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_entity")
data class TagEntity (
    @PrimaryKey val tagId: Long,
    val name: String = ""
)