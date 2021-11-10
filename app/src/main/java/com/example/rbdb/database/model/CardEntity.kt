package com.example.rbdb.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_entity")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    @ColumnInfo(name = "firstName") var firstName: String = "",
    @ColumnInfo(name = "lastName") var lastName: String ?= null,
    var business: String? = null,
    var position: String? = null,
    val dateAdded: String = "",
    var phone: String? = null,
    var email: String? = null,
    var description: String? = null,
    var isSelected: Boolean = false
)

