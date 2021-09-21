package com.example.rbdb.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CardWithTagsEntity(
    @Embedded val cardEntity: CardEntity,
    @Relation(
        parentColumn = "cardId",
        entityColumn = "tagId",
        associateBy = Junction(CardTagCrossRef::class)
    )
    val tags: List<TagEntity>
)