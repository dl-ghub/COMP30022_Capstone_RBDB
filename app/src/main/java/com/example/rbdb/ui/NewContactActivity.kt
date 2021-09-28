package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.rbdb.R
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.ActivityNewContactPageBinding
import java.text.SimpleDateFormat
import java.util.*

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.saveButton.setOnClickListener{
            saveItemToDatabase()
        }
    }

    private fun saveItemToDatabase() {
        var fieldError: Boolean = false

        val firstName = binding.firstNameInput.text.toString().trim()
        if (firstName.isEmpty()){
            binding.firstNameField.error = "* Required Field"
            fieldError = true
        }

        val lastName = binding.lastNameInput.text.toString().trim()
        if (firstName.isEmpty()){
            binding.lastNameField.error = "* Required Field"
            fieldError = true
        }

        val businessName = binding.businessNameInput.text.toString().trim()
        if (firstName.isEmpty()){
            binding.businessNameField.error = "* Required Field"
            fieldError = true
        }

        if (fieldError){return}

        binding.firstNameField.error = null
        binding.lastNameField.error = null
        binding.businessNameField.error = null

        val phoneNumber = binding.phoneInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val description = binding.descriptionInput.text.toString().trim()

        val cardEntity = CardEntity(
            name = firstName + lastName,
            business = businessName,
            dateAdded = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
            phone = phoneNumber,
            email = email,
            description = description
        )
    }



    override fun onSupportNavigateUp(): Boolean {
        //Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show()
        finish()
        //startActivity(Intent(this, MainActivity::class.java))
        return true
    }
}