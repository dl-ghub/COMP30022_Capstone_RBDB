package com.example.rbdb.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TagWithCardsEntity(
    @Embedded val tagEntity: TagEntity,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "cardId",
        associateBy = Junction(CardTagCrossRef::class)
    )
    val cards: List<CardEntity>
)