package com.example.rbdb.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["cardId", "tagId"],tableName = "card_tag_cross_ref")
data class CardTagCrossRef(
    val cardId: Long,
    val tagId: Long
)
