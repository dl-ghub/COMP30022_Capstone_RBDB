package com.example.rbdb.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.rbdb.database.dao.CardEntityDao
import com.example.rbdb.database.dao.ListEntityDao
import com.example.rbdb.database.dao.TagEntityDao
import com.example.rbdb.database.dao.UserEntityDao
import com.example.rbdb.database.model.*

@Database(
    entities = [CardEntity::class, UserEntity::class,
        CardListCrossRef::class,
        CardTagCrossRef::class,
        ListEntity::class,
        TagEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    // Singleton pattern for database initialization
    companion object {
        private var appDatabase : AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            if(appDatabase != null){
                return appDatabase!!
            }
            appDatabase = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "card-database").build()
            return appDatabase!!
        }
    }

    abstract fun cardEntityDao() : CardEntityDao

    abstract fun listEntityDao() : ListEntityDao

    abstract fun tagEntityDao() : TagEntityDao

    abstract fun userEntityDao() : UserEntityDao
}