package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_entity")
data class ListEntity (
    @PrimaryKey(autoGenerate = true) val listId: Long = 0,
    val name: String = ""
)