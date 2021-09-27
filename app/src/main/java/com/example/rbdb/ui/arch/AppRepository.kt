package com.example.rbdb.ui.arch

import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*

class AppRepository(private val appDatabase: AppDatabase) {
    // This class passes data between the view model and the database
    // its not technically necessary right now, but makes code extendable

    // Card dao interaction
    fun insertCard(cardEntity: CardEntity){appDatabase.cardEntityDao().insert(cardEntity)}

    fun deleteCard(cardEntity: CardEntity){appDatabase.cardEntityDao().delete(cardEntity)}

    fun updateCard(cardEntity: CardEntity){appDatabase.cardEntityDao().update(cardEntity)}

    fun getAllCards(): List<CardEntity>{return appDatabase.cardEntityDao().getAllCards()}

    fun getCardWithTags(): List<CardWithTagsEntity>{return appDatabase.cardEntityDao().getCardWithTags()}

    fun getCardWithLists(): List<CardWithListsEntity>{return appDatabase.cardEntityDao().getCardWithLists()}

    // List dao interaction
    fun insertList(listEntity: ListEntity){appDatabase.listEntityDao().insert(listEntity)}

    fun deleteList(listEntity: ListEntity){appDatabase.listEntityDao().delete(listEntity)}

    fun updateList(listEntity: ListEntity){appDatabase.listEntityDao().update(listEntity)}

    fun getAllLists(): List<ListEntity>{return appDatabase.listEntityDao().getAllLists()}

    fun getListWithCards(): List<ListWithCardsEntity>{return appDatabase.listEntityDao().getListWithCards()}

    // Tag dao interaction
    fun insertTag(tagEntity: TagEntity){appDatabase.tagEntityDao().insert(tagEntity)}

    fun deleteTag(tagEntity: TagEntity){appDatabase.tagEntityDao().delete(tagEntity)}

    fun updateTag(tagEntity: TagEntity){appDatabase.tagEntityDao().update(tagEntity)}

    fun getAllTags(): List<TagEntity>{return appDatabase.tagEntityDao().getAllTags()}

    fun getTagWithCards(): List<TagWithCardsEntity>{return appDatabase.tagEntityDao().getTagWithCards()}

    fun getTagID(nameOfTag: String): Long{return appDatabase.tagEntityDao().getTagID(nameOfTag)}

    // User dao interaction
    fun insertUser(userEntity : UserEntity){appDatabase.userEntityDao().insert(userEntity)}

    fun deleteUser(userEntity : UserEntity){appDatabase.userEntityDao().delete(userEntity)}

    fun updateUser(userEntity : UserEntity){appDatabase.userEntityDao().update(userEntity)}

    fun getAllUsers(): List<UserEntity>{return appDatabase.userEntityDao().getAllUsers()}

}