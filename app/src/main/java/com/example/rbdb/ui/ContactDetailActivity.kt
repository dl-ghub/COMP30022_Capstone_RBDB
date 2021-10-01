package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.rbdb.R
import com.example.rbdb.databinding.ActivityContactDetailBinding
import com.example.rbdb.databinding.ActivityMainBinding
import com.example.rbdb.ui.dataclasses.Contact


class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.cDetailTopAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val contact = intent.getSerializableExtra("contact") as? Contact ?: Contact(
            avatar = 0,
            name = "error",
            company = "something went wrong",
            phone = "error"
        )

        val nameTextView = binding.contactName
        val companyTextView = binding.tvCompany
        val descriptionTextView = binding.description
        val phoneTextView = binding.phoneNumber
        val emailTextView = binding.email

        nameTextView.text = contact.name
        companyTextView.text = contact.company
        phoneTextView.text = contact.phone

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.contact_detail_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        //Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show()
        finish()
        //startActivity(Intent(this, MainActivity::class.java))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit_menu_item -> {
            Toast.makeText(this, "edit pressed", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.delete_menu_item -> {
            Toast.makeText(this, "delete pressed", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}