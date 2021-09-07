package com.example.rbdb

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.rbdb.dao.CardEntityDao
import com.example.rbdb.model.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cardEntityDao() : CardEntityDao
}