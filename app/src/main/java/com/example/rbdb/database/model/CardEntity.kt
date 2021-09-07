package com.example.rbdb.database.model

import androidx.room.Entity

@Entity(tableName = "card_entity")
data class CardEntity (
    val id: Int = 0,
    val name: String = "",
    val business: String = "",
    val dateAdded: String = "",
    val phone: String? = null,
    val email: String? = null,
    val description: String? = null
)