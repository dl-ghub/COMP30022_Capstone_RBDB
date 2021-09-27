package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.rbdb.R

class NewContactPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact_page)

        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }
    override fun onSupportNavigateUp(): Boolean {
        //Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show()
        finish()
        //startActivity(Intent(this, MainActivity::class.java))
        return true
    }
}