package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.R

class MainActivity : AppCompatActivity() {



    // VV DEFAULT CODE BY IDE VV
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Recyclerview Implementation
        // Dummy data. Eventually will need to retrieve this in a similar format from DB.
        val dataForAdapter = listOf(
            Contact("Arnold Schwarzenegger", "Paramount", "03 5357 2225"),
            Contact("Barack Obama", "The White House", "03 5357 2225"),
            Contact("Bill Gates", "Microsoft", "03 5357 2225"),
            Contact("Chris Cuomo", "CNN", "03 5357 2225"),
            Contact("Elon Musk", "Space X", "03 5357 2225"),
            Contact("Jeff Bezos", "Amazon", "03 5357 2225")
        )

        val recyclerView: RecyclerView = findViewById(R.id.rvContacts)
        val contactAdapter = ContactAdapter()
        contactAdapter.setData(dataForAdapter)
        recyclerView.adapter = contactAdapter
        contactAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.search)
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is expanded", Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is collapsed", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        val searchItem = menu!!.findItem(R.id.search)
        var searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search here..."

        return true
    }
}