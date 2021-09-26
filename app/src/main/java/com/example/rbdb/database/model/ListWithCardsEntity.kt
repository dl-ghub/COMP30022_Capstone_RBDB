package com.example.rbdb.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ListWithCardsEntity(
    @Embedded val listEntity: ListEntity,
    @Relation(
        parentColumn = "listId",
        entityColumn = "cardId",
        associateBy = Junction(CardListCrossRef::class)
    )
    val cards: List<CardEntity>
)