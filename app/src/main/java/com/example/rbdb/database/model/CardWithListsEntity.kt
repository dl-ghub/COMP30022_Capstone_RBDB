package com.example.rbdb.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CardWithListsEntity(
    @Embedded val cardEntity: CardEntity,
    @Relation(
        parentColumn = "cardId",
        entityColumn = "listId",
        associateBy = Junction(CardListCrossRef::class)
    )
    val lists: List<ListEntity>
)