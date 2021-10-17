package com.example.rbdb.ui.arch

import androidx.room.Transaction
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.*


import androidx.sqlite.db.SimpleSQLiteQuery




class AppRepository(private val appDatabase: AppDatabase) {
    // This class passes data between the view model and the database
    // its not technically necessary right now, but makes code extendable

    // Card dao interaction
    suspend fun insertCard(cardEntity: CardEntity){appDatabase.cardEntityDao().insert(cardEntity)}

    suspend fun deleteCard(cardEntity: CardEntity){appDatabase.cardEntityDao().delete(cardEntity)}

    @Transaction
    suspend fun deleteCardAndCrossRefByCardId(cardId: Long){

        // remove the cross reference in "cardListCrossRef" table and "cardTagCrossRef" table
        appDatabase.cardListCrossRefDao().deleteByCardId(cardId)
        appDatabase.cardTagCrossRefDao().deleteByCardId(cardId)
        // finally, remove the card entity from the card enetity table
        appDatabase.cardEntityDao().deleteCardById(cardId)
    }

    suspend fun updateCard(cardEntity: CardEntity){appDatabase.cardEntityDao().update(cardEntity)}

    suspend fun getAllCards(): List<CardEntity>{return appDatabase.cardEntityDao().getAllCards()}

    suspend fun getCardWithTags(): List<CardWithTagsEntity>{return appDatabase.cardEntityDao().getCardWithTags()}

    suspend fun getCardWithLists(): List<CardWithListsEntity>{return appDatabase.cardEntityDao().getCardWithLists()}

    // Get cards by tag ids in OR relationship. You  have to provide at least one tag Id
    suspend fun getCardByTagIds(vararg tagIds: Long): List<CardEntity>{

        if(tagIds.size==0){
            //throw an exception if there is no tag ID provided
            throw Exception("You have to provide at least one tag Id")
        }
        //construct the query string
        var queryString:String = "SELECT CE.cardId,CE.name,CE.business,CE.dateAdded,CE.phone,CE.email,CE.description " +
                "FROM card_entity CE INNER JOIN card_tag_cross_ref CR ON CE.cardId = CR.cardId " +
                "INNER JOIN tag_entity TE ON CR.tagId = TE.tagId WHERE CR.tagId IN ("
        val formattedQuery:String = ","

        var argsList:ArrayList<Long> = arrayListOf()

        for (tagId: Long in tagIds){
            queryString = queryString+"?"
            queryString = queryString+formattedQuery
            argsList.add(tagId)
        }

        queryString = queryString.dropLast(formattedQuery.length)+")"+" GROUP BY CR.cardId";
        println("Query:getCardByTagIds = "+ queryString)

        //perform query
        val query = SimpleSQLiteQuery(queryString, argsList.toArray())
        return appDatabase.cardEntityDao().getCardByTagIds(query)

    }

    suspend fun getCardsByName(input : String): List<CardEntity>{return appDatabase.cardEntityDao().getCardsByName(input)}

    // List dao interaction
    suspend fun insertList(listEntity: ListEntity){appDatabase.listEntityDao().insert(listEntity)}

    suspend fun deleteList(listEntity: ListEntity){appDatabase.listEntityDao().delete(listEntity)}

    suspend fun updateList(listEntity: ListEntity){appDatabase.listEntityDao().update(listEntity)}

    suspend fun getAllLists(): List<ListEntity>{return appDatabase.listEntityDao().getAllLists()}

    suspend fun getListWithCards(): List<ListWithCardsEntity>{return appDatabase.listEntityDao().getListWithCards()}

    suspend fun getListWithCardsByListId(listId: Long): ListWithCardsEntity{return appDatabase.listEntityDao().getListWithCardsByListId(listId)}

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