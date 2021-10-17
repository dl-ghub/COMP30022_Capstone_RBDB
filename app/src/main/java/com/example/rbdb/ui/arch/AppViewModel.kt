package com.example.rbdb.ui.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.dao.CardEntityDao_Impl
import com.example.rbdb.database.model.*
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    private lateinit var repository: AppRepository

    fun init(appDatabase: AppDatabase) {
        repository = AppRepository(appDatabase)
    }

    // Call repository methods for card
    fun insertCard(cardEntity: CardEntity) {
        viewModelScope.launch { repository.insertCard(cardEntity) }
    }

    fun deleteCard(cardEntity: CardEntity) {
        viewModelScope.launch { repository.deleteCard(cardEntity) }
    }

    fun deleteCardAndCrossRefByCardId(cardId: Long) {
        viewModelScope.launch { repository.deleteCardAndCrossRefByCardId(cardId) }
    }

    fun updateCard(cardEntity: CardEntity) {
        viewModelScope.launch { repository.updateCard(cardEntity) }
    }

    fun getAllCards(): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllCards()) }
        return result
    }

    fun getCardWithTags(): LiveData<List<CardWithTagsEntity>> {
        val result = MutableLiveData<List<CardWithTagsEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardWithTags()) }
        return result
    }

    fun getCardWithLists(): LiveData<List<CardWithListsEntity>> {
        val result = MutableLiveData<List<CardWithListsEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardWithLists()) }
        return result
    }

    fun getCardsByName(input : String): LiveData<List<CardEntity>> {
        val result = MutableLiveData<List<CardEntity>>()
        viewModelScope.launch { result.postValue(repository.getCardsByName(input)) }
        return result
    }

    // Call repository methods for list
    fun insertList(listEntity: ListEntity) {
        viewModelScope.launch { repository.insertList(listEntity) }
    }

    fun deleteList(listEntity: ListEntity) {
        viewModelScope.launch { repository.deleteList(listEntity) }
    }

    fun updateList(listEntity: ListEntity) {
        viewModelScope.launch { repository.updateList(listEntity) }
    }

    fun getAllLists(): LiveData<List<ListEntity>> {
        val result = MutableLiveData<List<ListEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllLists()) }
        return result
    }

    fun getListWithCards(): LiveData<List<ListWithCardsEntity>> {
        val result = MutableLiveData<List<ListWithCardsEntity>>()
        viewModelScope.launch { result.postValue(repository.getListWithCards()) }
        return result
    }

    // Call repository methods for tag
    fun insertTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.insertTag(tagEntity) }
    }

    fun deleteTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.deleteTag(tagEntity) }
    }

    fun updateTag(tagEntity: TagEntity) {
        viewModelScope.launch { repository.updateTag(tagEntity) }
    }

    fun getAllTags(): LiveData<List<TagEntity>> {
        val result = MutableLiveData<List<TagEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllTags()) }
        return result
    }

    fun getTagWithCards(): LiveData<List<TagWithCardsEntity>> {
        val result = MutableLiveData<List<TagWithCardsEntity>>()
        viewModelScope.launch { result.postValue(repository.getTagWithCards()) }
        return result
    }

    fun getTagID(nameOfTag: String): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch { result.postValue(repository.getTagID(nameOfTag)) }
        return result
    }

    // Call repository methods for user
    fun insertUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.insertUser(userEntity) }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.deleteUser(userEntity) }
    }

    fun updateUser(userEntity: UserEntity) {
        viewModelScope.launch { repository.updateUser(userEntity) }
    }

    fun getAllUsers(): LiveData<List<UserEntity>> {
        val result = MutableLiveData<List<UserEntity>>()
        viewModelScope.launch { result.postValue(repository.getAllUsers()) }
        return result
    }
}