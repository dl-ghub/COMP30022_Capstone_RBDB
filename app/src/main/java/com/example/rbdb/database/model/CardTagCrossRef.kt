package com.example.rbdb.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["cardId", "tagId"],tableName = "card_tag_cross_ref",indices = [Index(value = ["cardId", "tagId"])])
data class CardTagCrossRef(
    val cardId: Long,
    @ColumnInfo(index = true)
    val tagId: Long
)
