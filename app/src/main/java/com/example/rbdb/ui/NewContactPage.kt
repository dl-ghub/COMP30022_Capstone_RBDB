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
        toolbar.setNavigationOnClickListener {
            // Handle navigation icon press (go back to home page)
            super.onBackPressed()
        }
    }
}