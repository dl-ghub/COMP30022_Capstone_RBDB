package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.rbdb.R
import com.example.rbdb.databinding.ActivityContactDetailBinding


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

        val contactName = intent.getStringExtra("contact_name")
        val contactBusiness = intent.getStringExtra("contact_business")
        val contactDateAdded = intent.getStringExtra("contact_dateAdded")
        val contactPhone = intent.getStringExtra("contact_phone")
        val contactEmail = intent.getStringExtra("contact_email")
        val contactDescription = intent.getStringExtra("contact_description")

        /* Generate Default Letter Circle Avatar */
        val avatar = contactName?.let { createAvatar(it) }

        binding.contactPhoto.setImageDrawable(avatar)
        binding.contactName.text = contactName
        binding.tvCompany.text = contactBusiness
        binding.phoneNumber.text = contactPhone
        binding.email.text = contactEmail
        binding.description.text = contactDescription

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

    private fun createAvatar(name: String): TextDrawable {
        val firstInitial = name[0].toString()
        val whiteSpaceIndex = name.indexOf(" ")
        val secondInitial = name[whiteSpaceIndex + 1].toString()
        val initials = firstInitial + secondInitial
        val generator: ColorGenerator = ColorGenerator.MATERIAL

        return TextDrawable.builder().buildRound(initials, generator.getColor(secondInitial))
    }
}