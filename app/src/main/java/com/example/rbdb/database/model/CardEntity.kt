package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_entity")
data class CardEntity (
    @PrimaryKey val cardId: Long,
    val name: String = "",
    val business: String = "",
    val dateAdded: String = "",
    val phone: String? = null,
    val email: String? = null,
    val description: String? = null
)