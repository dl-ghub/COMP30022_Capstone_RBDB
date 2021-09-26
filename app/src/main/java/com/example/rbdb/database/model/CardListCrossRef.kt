package com.example.rbdb.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["cardId", "listId"])
data class CardListCrossRef(
    val cardId: Long,
    val listId: Long
)