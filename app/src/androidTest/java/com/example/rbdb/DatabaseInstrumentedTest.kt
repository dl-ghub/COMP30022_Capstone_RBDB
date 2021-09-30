package com.example.rbdb

import android.content.Context
import android.nfc.Tag
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
    fun addCardEntity() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        cardEntityDao.insert(cardEntity)

        val allCards = cardEntityDao.getAllCards()
        println("hello i am here")
        println(allCards);

        assertThat(allCards.isEmpty(), equalTo(false))
    }


    @Test
    @Throws(Exception::class)
    fun searchCardByName() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        cardEntityDao.insert(cardEntity)

        val cards = cardEntityDao.getCardsByName("Sam");
        println("0930 hello")
        println(cards);

        assertThat(cards[0], equalTo(cardEntity))
    }

    @Test
    @Throws(Exception::class)
    fun getAllCardsOrderByName() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            0, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            0, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            0, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            0, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val cards = cardEntityDao.getAllCardsOrderByName();
        println("0930 hello")
        for (card in cards) {
            println(card);
        }

        assertThat(cards[0].name, equalTo("adam"))
        assertThat(cards[1].name, equalTo("jack"))
        assertThat(cards[2].name, equalTo("peter"))
        assertThat(cards[3].name, equalTo("sam"))
        assertThat(cards[4].name, equalTo("Zerg"))


//        assertThat(cards[0], equalTo(cardEntity))
    }

    @Test
    @Throws(Exception::class)
    fun searchByKeywordIndescription() = runBlocking {
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity1: CardEntity = CardEntity(
            1, "sam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a cool guy"
        )
        val cardEntity2: CardEntity = CardEntity(
            0, "jack", "unimelb",
            "0922", "444222999", "test@email.com", "I am a red guy"
        )
        val cardEntity3: CardEntity = CardEntity(
            0, "peter", "unimelb",
            "0922", "444222999", "test@email.com", "I am a blue guy"
        )
        val cardEntity4: CardEntity = CardEntity(
            100, "adam", "unimelb",
            "0922", "444222999", "test@email.com", "I am a green guy"
        )
        val cardEntity5: CardEntity = CardEntity(
            0, "Zerg", "unimelb",
            "0922", "444222999", "test@email.com", "I am a yellow guy"
        )

        cardEntityDao.insert(cardEntity1)
        cardEntityDao.insert(cardEntity2)
        cardEntityDao.insert(cardEntity3)
        cardEntityDao.insert(cardEntity4)
        cardEntityDao.insert(cardEntity5)

        val cards = cardEntityDao.getCardsByKeywordInDescription("green");
        println("0930 hello")
        for (card in cards) {
            println(card);
        }

        assertThat(cards.size, equalTo(1));
        assertThat(cards[0], equalTo(cardEntity4));

//        assertThat(cards[0], equalTo(cardEntity))
    }

    


    //TODO: implement this test
    /*@Test
    @Throws(Exception::class)
    fun searchCardByTag() = runBlocking{
//        val user: User = TestUtil.createUser(3).apply {
//            setName("george")
//        }
        val cardEntity: CardEntity = CardEntity(1,"sam","unimelb",
            "0922","444222999","test@email.com","I am a cool guy")

        cardEntityDao.insert(cardEntity)

        val tagEntity:TagEntity = TagEntity(1)

        val cards = cardEntityDao.getCardByName("Sam");
        println("0930 hello")
        println(cards);

        assertThat(cards[0], equalTo(cardEntity))
    }*/


}