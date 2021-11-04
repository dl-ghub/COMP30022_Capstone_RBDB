package com.example.rbdb.ui.arch

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.*
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*


import androidx.sqlite.db.SimpleSQLiteQuery


class AppRepository(private val appDatabase: AppDatabase) {
    // This class passes data between the view model and the database
    // its not technically necessary right now, but makes code extendable

    // Card dao interaction
    suspend fun insertCard(cardEntity: CardEntity): Long {return appDatabase.cardEntityDao().insert(cardEntity)}


    suspend fun deleteCard(cardEntity: CardEntity) {
        appDatabase.cardEntityDao().delete(cardEntity)
    }

    suspend fun getCardById(id: Long): CardEntity {
        return appDatabase.cardEntityDao().getCardById(id)
    }

    @Transaction
    suspend fun deleteCardAndCrossRefByCardId(cardId: Long) {

        // remove the cross reference in "cardListCrossRef" table and "cardTagCrossRef" table
        appDatabase.cardListCrossRefDao().deleteByCardId(cardId)
        appDatabase.cardTagCrossRefDao().deleteByCardId(cardId)
        // finally, remove the card entity from the card entity table
        appDatabase.cardEntityDao().deleteCardById(cardId)
    }

    suspend fun updateCard(cardEntity: CardEntity) {
        appDatabase.cardEntityDao().update(cardEntity)
    }

    suspend fun getAllCards(): List<CardEntity> {
        return appDatabase.cardEntityDao().getAllCards()
    }

    suspend fun getCardWithTags(): List<CardWithTagsEntity> {
        return appDatabase.cardEntityDao().getCardWithTags()
    }

    suspend fun getCardWithLists(): List<CardWithListsEntity> {
        return appDatabase.cardEntityDao().getCardWithLists()
    }

    //search the require cards by name.
    //it returns cards that contain cardName as a substring, ordered by the length of the names of the cards
    suspend fun getCardsByName(cardName: String): List<CardEntity> {
        return appDatabase.cardEntityDao().getCardsByName(cardName)
    }

    suspend fun getAllCardsOrderByName(): List<CardEntity> {
        return appDatabase.cardEntityDao().getAllCardsOrderByName()
    }

    suspend fun getCardsByKeywordInDescription(keyword: String): List<CardEntity> {
        return appDatabase.cardEntityDao().getCardsByKeywordInDescription(keyword)
    }

    // Get cards by tag ids in OR relationship. You  have to provide at least one tag Id
    suspend fun getCardByTagIds(tagIds: List<Long>): List<CardEntity>{

        if(tagIds.isEmpty()){

            // return empty card entity list if there is no tag selected
            return listOf()
        }
        //construct the query string
        var queryString: String =
            "SELECT CE.cardId,CE.name,CE.business,CE.dateAdded,CE.phone,CE.email,CE.description " +
                    "FROM card_entity CE INNER JOIN card_tag_cross_ref CR ON CE.cardId = CR.cardId " +
                    "INNER JOIN tag_entity TE ON CR.tagId = TE.tagId WHERE CR.tagId IN ("
        val formattedQuery: String = ","

        val argsList: ArrayList<Long> = arrayListOf()

        for (tagId: Long in tagIds) {
            queryString += "?"
            queryString += formattedQuery
            argsList.add(tagId)
        }

        queryString = queryString.dropLast(formattedQuery.length)+")"+" GROUP BY CR.cardId" + " ORDER BY UPPER(CE.name) ASC"
        println("Query:getCardByTagIds = $queryString")


        //perform query
        val query = SimpleSQLiteQuery(queryString, argsList.toArray())
        return appDatabase.cardEntityDao().getCardByTagIds(query)

    }

    //TODO: change this
    //Get cards by tag ids in OR relationship. You  have to provide at least one tag Id
    @SuppressLint("LongLogTag")
    suspend fun getCardByKeywordInSelectedColumns(
        keyword: String,
        columns: List<String>
    ): List<CardEntity> {

        if (columns.isEmpty()) {
            //throw an exception if there is no column chosen
            throw Exception("You have to choose at least one column to search")
        }

        //SELECT * FROM card_entity WHERE name like '%1%' OR business like '%1%'
        //construct the query string
        var queryString: String = "SELECT * FROM card_entity WHERE "
        val formattedQuery: String = " like '%$keyword%' "
        val logicQuery: String = " OR "


        for (column: String in columns) {
            queryString += column
            queryString += formattedQuery
            queryString += logicQuery
        }

        queryString = queryString.dropLast(logicQuery.length)
        queryString += " ORDER BY UPPER(name) ASC"
//        println("Query:getCardByKeywordInSelectedColumns = "+ queryString)
        Log.d("Query:getCardByKeywordInSelectedColumns", queryString)

        //perform query
        val query = SimpleSQLiteQuery(queryString)
        return appDatabase.cardEntityDao().getCardByKeywordInSelectedColumns(query)

    }

    // List dao interaction
    suspend fun getListById(id: Long): ListEntity {
        return appDatabase.listEntityDao().getListById(id)
    }

    suspend fun insertList(listEntity: ListEntity): Long {
        val x = appDatabase.listEntityDao().insert(listEntity)
        Log.d("groupId repo", x.toString())
        return x
    }

    suspend fun deleteList(listEntity: ListEntity) {
        appDatabase.listEntityDao().delete(listEntity)
    }

    @Transaction
    suspend fun deleteListAndCrossRefByListId(listId: Long) {

        // remove the cross references in "cardListCrossRef"
        appDatabase.cardListCrossRefDao().deleteAllByListId(listId)
        // remove the list entity from the list entity table
        appDatabase.listEntityDao().deleteByListId(listId)
    }

    suspend fun updateList(listEntity: ListEntity){appDatabase.listEntityDao().update(listEntity)}

    suspend fun updateListName(name: String, listId: Long) {
        appDatabase.listEntityDao().updateListName(name, listId)
    }

    suspend fun deleteByListId(listId: Long) {
        appDatabase.listEntityDao().deleteByListId(listId)
    }

    suspend fun getAllLists(): List<ListEntity> {
        return appDatabase.listEntityDao().getAllLists()
    }

    suspend fun getListWithCards(): List<ListWithCardsEntity> {
        return appDatabase.listEntityDao().getListWithCards()
    }

    suspend fun getListWithCardsByListId(listId: Long): ListWithCardsEntity {
        return appDatabase.listEntityDao().getListWithCardsByListId(listId)
    }

    // Tag dao interaction
    suspend fun insertTag(tagEntity: TagEntity) {
        appDatabase.tagEntityDao().insert(tagEntity)
    }

    suspend fun deleteTag(tagEntity: TagEntity) {
        appDatabase.tagEntityDao().delete(tagEntity)
    }

    @Transaction
    suspend fun deleteTagAndCrossRefByTagId(tagId: Long){

        // NOTE: had to do it in this order to solve a front-end bug
        // remove the tag entity from the tag entity table
        appDatabase.tagEntityDao().deleteByTagId(tagId)
        // remove the cross references in "cardTagCrossRef"
        appDatabase.cardTagCrossRefDao().deleteByTagId(tagId)
    }

    suspend fun updateTag(tagEntity: TagEntity){appDatabase.tagEntityDao().update(tagEntity)}

    suspend fun getAllTags(): List<TagEntity> {
        return appDatabase.tagEntityDao().getAllTags()
    }

    suspend fun getTagWithCards(): List<TagWithCardsEntity> {
        return appDatabase.tagEntityDao().getTagWithCards()
    }

    suspend fun getTagID(nameOfTag: String): Long {
        return appDatabase.tagEntityDao().getTagID(nameOfTag)
    }

    suspend fun getTagsByCardId(cardId: Long): List<TagEntity>{return appDatabase.tagEntityDao().getTagsByCardId(cardId)}

    // User dao interaction
    suspend fun insertUser(userEntity: UserEntity) {
        appDatabase.userEntityDao().insert(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity) {
        appDatabase.userEntityDao().delete(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        appDatabase.userEntityDao().update(userEntity)
    }

    suspend fun getAllUsers(): List<UserEntity> {
        return appDatabase.userEntityDao().getAllUsers()
    }

    // CardListCrossRefDao interaction
    suspend fun insertCardListCrossRef(cardListCrossRef: CardListCrossRef){appDatabase.cardListCrossRefDao().insert(cardListCrossRef)}

    suspend fun deleteCardListCrossRef(cardListCrossRef: CardListCrossRef){appDatabase.cardListCrossRefDao().delete(cardListCrossRef)}

    suspend fun deleteAllCrossRefByListId(listId: Long) {
        appDatabase.cardListCrossRefDao().deleteAllByListId(listId)
    }

    suspend fun deleteCardListCrossRefByCardId(cardId: Long) {
        appDatabase.cardListCrossRefDao().deleteByCardId(cardId)
    }

    suspend fun updateCardListCrossRef(cardListCrossRef: CardListCrossRef) {
        appDatabase.cardListCrossRefDao().update(cardListCrossRef)
    }

    suspend fun getAllCardListCrossRef(): List<CardListCrossRef> {
        return appDatabase.cardListCrossRefDao().getAllCardListCrossRef()
    }

    // CardTagCrossRefDao interaction
    suspend fun insertCardTagCrossRef(cardTagCrossRef: CardTagCrossRef) {
        appDatabase.cardTagCrossRefDao().insert(cardTagCrossRef)
    }

    suspend fun deleteCardTagCrossRef(cardTagCrossRef: CardTagCrossRef) {
        appDatabase.cardTagCrossRefDao().delete(cardTagCrossRef)
    }

    suspend fun deleteCardTagCrossRefByCardId(cardId: Long) {
        appDatabase.cardTagCrossRefDao().deleteByCardId(cardId)
    }

    suspend fun updateCardTagCrossRef(cardTagCrossRef: CardTagCrossRef) {
        appDatabase.cardTagCrossRefDao().update(cardTagCrossRef)
    }

    suspend fun getAllCardTagCrossRef(): List<CardTagCrossRef> {
        return appDatabase.cardTagCrossRefDao().getAllCardTagCrossRef()
    }

    suspend fun deleteAllTagCrossRefByCardId(cardId: Long){appDatabase.cardTagCrossRefDao().deleteAllByCardId(cardId)}
}