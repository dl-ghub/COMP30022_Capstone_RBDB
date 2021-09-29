package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_entity")
data class TagEntity (
    @PrimaryKey(autoGenerate = true) val tagId: Long = 0,
    val name: String = ""
)