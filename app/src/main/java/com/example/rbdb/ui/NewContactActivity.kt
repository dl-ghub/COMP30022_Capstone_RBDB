package com.example.rbdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.CardTagCrossRef
import com.example.rbdb.database.model.TagEntity
import com.example.rbdb.databinding.ActivityNewContactPageBinding
import com.example.rbdb.ui.arch.AppViewModel
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

class NewContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewContactPageBinding
    private val viewModel: AppViewModel by viewModels()
    private var tagsList: List<TagEntity> = mutableListOf()
    private var chipList = ArrayList<TagEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.saveButton.setOnClickListener {
            saveItemToDatabase()
        }
        viewModel.init(AppDatabase.getDatabase(this))
        viewModel.getAllTags().observe(this, { tags ->
            tagsList = tags
            updateChips(tags)
        })
    }

    private fun saveItemToDatabase() {
        var fieldError = false

        val firstName = binding.firstNameInput.text.toString().trim()
        if (firstName.isEmpty()) {
            binding.firstNameField.error = "* Required Field"
            fieldError = true
        } else {
            binding.firstNameField.error = null
        }

        val lastName = binding.lastNameInput.text.toString().trim()
        if (lastName.isEmpty()) {
            binding.lastNameField.error = "* Required Field"
            fieldError = true
        } else {
            binding.lastNameField.error = null
        }

        val businessName = binding.businessNameInput.text.toString().trim()
        if (businessName.isEmpty()) {
            binding.businessNameField.error = "* Required Field"
            fieldError = true
        } else {
            binding.businessNameField.error = null
        }

        if (fieldError) {
            return
        }

        val phoneNumber = binding.phoneInput.text.toString().trim()
        if (isPhoneValid(binding.phoneInput.text!!) && phoneNumber.isNotEmpty()){
            binding.phoneField.error = "* Invalid Phone Number"
            fieldError = true
        }
        else{binding.phoneField.error = null}

        val email = binding.emailInput.text.toString().trim()
        if (isEmailValid(binding.emailInput.text!!) && email.isNotEmpty()){
            binding.emailField.error = "* Invalid Email"
            fieldError = true
        }
        else{binding.emailField.error = null}

        if (fieldError){return}

        val description = binding.descriptionInput.text.toString().trim()

        val cardEntity = CardEntity(
            name = "$firstName $lastName",
            business = businessName,
            dateAdded = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault()).format(Date()),
            phone = phoneNumber,
            email = email,
            description = description
        )

        // Insert card to database. Also creates and inserts all the CardTagCrossRefs
        viewModel.insertCard(cardEntity).observe(this, { newContactId ->
            Log.d("New contact Id", newContactId.toString())
            // Insert tag cross references to database.
            for (selectedTag in chipList) {
                val cardTagCrossRef = CardTagCrossRef(
                    cardId = newContactId,
                    tagId = selectedTag.tagId
                )
                viewModel.insertCardTagCrossRef(cardTagCrossRef)
                Log.d("cardTagCrossRef add", cardTagCrossRef.toString())
            }
        })


        //Return to Homepage or previous page code
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        //
        finish()
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(phone: CharSequence): Boolean {
        return !Patterns.PHONE.matcher(phone).matches()
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

        builder.setPositiveButton("Yes") { _, _ -> super.onBackPressed() }

        builder.setNegativeButton("Cancel") { _, _ -> }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    private fun addListenerOnButtonClick() {
        val chipGroup = binding.tagChipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    for (tag in tagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.add(tag)
                            Log.d("chipList", chipList.toString())
                        }
                    }
                } else {
                    for (tag in tagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.remove(tag)
                            Log.d("chipList", chipList.toString())
                        }
                    }
                }
            }
        }
    }

    private fun updateChips(tags: List<TagEntity>) {
        val chipGroup = binding.tagChipGroup
        chipGroup.removeAllViewsInLayout()
        for (tag in tags) {
            val chip = layoutInflater.inflate(R.layout.layout_chip_choice, chipGroup, false) as Chip
            chip.text = (tag.name)
            chip.id = (tag.tagId.toInt())
            chipGroup.addView(chip)
        }
        addListenerOnButtonClick()
    }
}