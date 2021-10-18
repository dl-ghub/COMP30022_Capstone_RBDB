package com.example.rbdb.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_entity")
data class ListEntity (
    @PrimaryKey(autoGenerate = true) val listId: Long = 0,
    @ColumnInfo(name = "name") var name: String = ""
)