package com.example.rbdb.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEntity (
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val name: String = ""
)