package com.example.rbdb

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.dao.CardEntityDao
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.CardListCrossRef
import com.example.rbdb.database.model.TagEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Appendable
import java.util.concurrent.Executors


@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {


    //    private lateinit var userDao: UserDao
    private lateinit var cardEntityDao: CardEntityDao

    //    private lateinit var db: TestDatabase
    private lateinit var db: AppDatabase
//
//    @Before
//    fun createDb() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, TestDatabase::class.java).build()
//        userDao = db.getUserDao()
//    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        cardEntityDao = db.cardEntityDao()
    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

//
//    @Test
//    @Throws(Exception::class)
//    fun writeUserAndReadInList() {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
//        userDao.insert(user)
//        val byName = userDao.findUsersByName("george")
//        assertThat(byName.get(0), equalTo(user))
//    }

    @Test
    @Throws(Exception::class)
    fun addCardEntity() = runBlocking{
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(1,"sam","unimelb",
            "0922","444222999","test@email.com","I am a cool guy")

        cardEntityDao.insert(cardEntity)

        val allCards = cardEntityDao.getAllCards()
        println("hello i am here")
        println(allCards);

        assertThat(allCards.get(0), equalTo(cardEntity))
    }


    @Test
    @Throws(Exception::class)
    fun searchCardByName() = runBlocking{
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(1,"sam","unimelb",
            "0922","444222999","test@email.com","I am a cool guy")

        cardEntityDao.insert(cardEntity)

        val cards = cardEntityDao.getCardByName("Sam");
        println("0930 hello")
        println(cards);

        assertThat(cards[0], equalTo(cardEntity))
    }


}