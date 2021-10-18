package com.example.rbdb.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_entity")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    @ColumnInfo(name = "name") var name: String = "",
    var business: String = "",
    val dateAdded: String = "",
    val phone: String? = null,
    val email: String? = null,
    val description: String? = null,
    var isSelected: Boolean = false
)

