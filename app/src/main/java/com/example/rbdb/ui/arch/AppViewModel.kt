package com.example.rbdb.ui.arch

import androidx.lifecycle.ViewModel
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*

class AppViewModel(): ViewModel() {

    private lateinit var repository: AppRepository

    fun init(appDatabase: AppDatabase){
        repository = AppRepository(appDatabase)
    }

    // Call repository methods for card
    fun insertCard(cardEntity: CardEntity){repository.insertCard(cardEntity)}

    fun deleteCard(cardEntity: CardEntity){repository.deleteCard(cardEntity)}

    fun updateCard(cardEntity: CardEntity){repository.updateCard(cardEntity)}

    fun getAllCards(): List<CardEntity>{return repository.getAllCards()}

    fun getCardWithTags(): List<CardWithTagsEntity>{return repository.getCardWithTags()}

    fun getCardWithLists(): List<CardWithListsEntity>{return repository.getCardWithLists()}

    // Call repository methods for list
    fun insertList(listEntity: ListEntity){repository.insertList(listEntity)}

    fun deleteList(listEntity: ListEntity){repository.deleteList(listEntity)}

    fun updateList(listEntity: ListEntity){repository.updateList(listEntity)}

    fun getAllLists(): List<ListEntity>{return repository.getAllLists()}

    fun getListWithCards(): List<ListWithCardsEntity>{return repository.getListWithCards()}

    // Call repository methods for tag
    fun insertTag(tagEntity: TagEntity){repository.insertTag(tagEntity)}

    fun deleteTag(tagEntity: TagEntity){repository.deleteTag(tagEntity)}

    fun updateTag(tagEntity: TagEntity){repository.updateTag(tagEntity)}

    fun getAllTags(): List<TagEntity>{return repository.getAllTags()}

    fun getTagWithCards(): List<TagWithCardsEntity>{return repository.getTagWithCards()}

    fun getTagID(nameOfTag: String): Long{return repository.getTagID(nameOfTag)}

    // Call repository methods for user
    fun insertUser(userEntity : UserEntity){repository.insertUser(userEntity)}

    fun deleteUser(userEntity : UserEntity){repository.deleteUser(userEntity)}

    fun updateUser(userEntity : UserEntity){repository.updateUser(userEntity)}

    fun getAllUsers(): List<UserEntity>{return repository.getAllUsers()}
}