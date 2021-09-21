package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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