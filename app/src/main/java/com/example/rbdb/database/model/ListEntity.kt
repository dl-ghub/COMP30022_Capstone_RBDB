package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_entity")
data class ListEntity (
    @PrimaryKey val listId: Long,
    val name: String = ""
)