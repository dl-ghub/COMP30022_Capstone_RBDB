package com.example.rbdb.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["cardId", "listId"],tableName = "card_list_cross_ref")
data class CardListCrossRef(
    val cardId: Long,
    val listId: Long
)