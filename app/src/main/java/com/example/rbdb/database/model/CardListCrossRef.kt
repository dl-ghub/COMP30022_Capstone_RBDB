package com.example.rbdb.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["cardId", "listId"],tableName = "card_list_cross_ref",indices = [Index(value = ["cardId", "listId"])])
data class CardListCrossRef(
    val cardId: Long,
    @ColumnInfo(index = true)
    val listId: Long
)