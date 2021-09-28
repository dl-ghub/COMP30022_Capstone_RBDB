package com.example.rbdb.ui.arch

import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*

class AppRepository(private val appDatabase: AppDatabase) {
    // This class passes data between the view model and the database
    // its not technically necessary right now, but makes code extendable

    // Card dao interaction
    suspend fun insertCard(cardEntity: CardEntity){appDatabase.cardEntityDao().insert(cardEntity)}

    suspend fun deleteCard(cardEntity: CardEntity){appDatabase.cardEntityDao().delete(cardEntity)}

    suspend fun updateCard(cardEntity: CardEntity){appDatabase.cardEntityDao().update(cardEntity)}

    suspend fun getAllCards(): List<CardEntity>{return appDatabase.cardEntityDao().getAllCards()}

    suspend fun getCardWithTags(): List<CardWithTagsEntity>{return appDatabase.cardEntityDao().getCardWithTags()}

    suspend fun getCardWithLists(): List<CardWithListsEntity>{return appDatabase.cardEntityDao().getCardWithLists()}

    suspend fun getCardbyId(id: Long): CardEntity{return appDatabase.cardEntityDao().getCardById(id)}


    // List dao interaction
    suspend fun insertList(listEntity: ListEntity){appDatabase.listEntityDao().insert(listEntity)}

    suspend fun deleteList(listEntity: ListEntity){appDatabase.listEntityDao().delete(listEntity)}

    suspend fun updateList(listEntity: ListEntity){appDatabase.listEntityDao().update(listEntity)}

    suspend fun getAllLists(): List<ListEntity>{return appDatabase.listEntityDao().getAllLists()}

    suspend fun getListWithCards(): List<ListWithCardsEntity>{return appDatabase.listEntityDao().getListWithCards()}

    // Tag dao interaction
    suspend fun insertTag(tagEntity: TagEntity){appDatabase.tagEntityDao().insert(tagEntity)}

    suspend fun deleteTag(tagEntity: TagEntity){appDatabase.tagEntityDao().delete(tagEntity)}

    suspend fun updateTag(tagEntity: TagEntity){appDatabase.tagEntityDao().update(tagEntity)}

    suspend fun getAllTags(): List<TagEntity>{return appDatabase.tagEntityDao().getAllTags()}

    suspend fun getTagWithCards(): List<TagWithCardsEntity>{return appDatabase.tagEntityDao().getTagWithCards()}

    suspend fun getTagID(nameOfTag: String): Long{return appDatabase.tagEntityDao().getTagID(nameOfTag)}

    // User dao interaction
    suspend fun insertUser(userEntity : UserEntity){appDatabase.userEntityDao().insert(userEntity)}

    suspend fun deleteUser(userEntity : UserEntity){appDatabase.userEntityDao().delete(userEntity)}

    suspend fun updateUser(userEntity : UserEntity){appDatabase.userEntityDao().update(userEntity)}

    suspend fun getAllUsers(): List<UserEntity>{return appDatabase.userEntityDao().getAllUsers()}

}