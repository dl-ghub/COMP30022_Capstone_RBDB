package com.example.rbdb.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["cardId", "tagId"])
data class CardTagCrossRef(
    val cardId: Long,
    val tagId: Long
)
