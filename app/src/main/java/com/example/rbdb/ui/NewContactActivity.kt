package com.example.rbdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.ActivityNewContactPageBinding
import com.example.rbdb.ui.arch.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactPageBinding
    private val viewModel: AppViewModel by viewModels()

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
        viewModel.init(AppDatabase.getDatabase(this))
    }

    private fun saveItemToDatabase() {
        var fieldError = false

        val firstName = binding.firstNameInput.text.toString().trim()
        if (firstName.isEmpty()){
            binding.firstNameField.error = "* Required Field"
            fieldError = true
        }
        else{ binding.firstNameField.error = null }

        val lastName = binding.lastNameInput.text.toString().trim()
        if (lastName.isEmpty()){
            binding.lastNameField.error = "* Required Field"
            fieldError = true
        }
        else{binding.lastNameField.error = null}

        val businessName = binding.businessNameInput.text.toString().trim()
        if (businessName.isEmpty()){
            binding.businessNameField.error = "* Required Field"
            fieldError = true
        }
        else{binding.businessNameField.error = null}

        if (fieldError){return}

        val phoneNumber = binding.phoneInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val description = binding.descriptionInput.text.toString().trim()

        val cardEntity = CardEntity(
            name = "$firstName $lastName",
            business = businessName,
            dateAdded = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
            phone = phoneNumber,
            email = email,
            description = description
        )
        viewModel.insertCard(cardEntity)

        //Return to Homepage or previous page code
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        //
        finish()
    }

    
    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()

        //startActivity(Intent(this, MainActivity::class.java))
        return true
    }

    override fun onBackPressed() {
//        Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.confirm_leave_title)
        builder.setMessage(R.string.confirm_leave_txt)

        builder.setPositiveButton("Yes"){ _, _ -> super.onBackPressed() }

        builder.setNegativeButton("Cancel"){_, _ -> }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}