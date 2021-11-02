package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.CardTagCrossRef
import com.example.rbdb.database.model.TagEntity
import com.example.rbdb.databinding.ActivityEditContactBinding
import com.example.rbdb.ui.arch.AppViewModel
import com.google.android.material.chip.Chip
import java.util.*

class EditContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditContactBinding
    private val viewModel: AppViewModel by viewModels()
    private var contactTagsList: List<TagEntity> = mutableListOf()
    private var allTagsList: List<TagEntity> = mutableListOf()
    private var chipList = ArrayList<TagEntity>()
    private var contactId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditContactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Edit Contact"
        contactId = intent.getLongExtra(ContactDetailActivity.CONTACT_ID, -1)
        viewModel.init(AppDatabase.getDatabase(this))
        val contactObserver = Observer<CardEntity> { contact ->
            binding.firstNameInput.setText(contact.name)
            //binding.lastNameInput.setText(contact.name)
            binding.businessNameInput.setText(contact.business)
            binding.phoneInput.setText(contact.phone)
            binding.emailInput.setText(contact.email)
            binding.descriptionInput.setText(contact.description)
        }

        binding.saveButton.setOnClickListener {
            saveItemToDatabase()
        }
        viewModel.getCardById(contactId).observe(this, contactObserver)

        viewModel.getTagsByCardId(contactId).observe(this, { contactTags ->
            contactTagsList = contactTags
            for (tag in contactTags) {
                chipList.add(tag)
            }
            viewModel.getAllTags().observe(this, { allTags ->
                allTagsList = allTags
                updateChips(allTags)
            })
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
        if (isPhoneValid(binding.phoneInput.text!!) && phoneNumber.isNotEmpty()) {
            binding.phoneField.error = "* Invalid Phone Number"
            fieldError = true
        } else {
            binding.phoneField.error = null
        }

        val email = binding.emailInput.text.toString().trim()
        if (isEmailValid(binding.emailInput.text!!) && email.isNotEmpty()) {
            binding.emailField.error = "* Invalid Email"
            fieldError = true
        } else {
            binding.emailField.error = null
        }

        if (fieldError) {
            return
        }

        val description = binding.descriptionInput.text.toString().trim()

        val updateObserver = Observer<CardEntity> { contact ->
            contact.name = firstName
            contact.business = businessName
            contact.phone = phoneNumber
            contact.email = email
            contact.description = description
            viewModel.updateCard(contact)
        }
        viewModel.getCardById(contactId).observe(this, updateObserver)
        updateContactTags()
        //Return to Homepage or previous page code
        /*val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)*/
        //
        //super.onBackPressed()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            finish()
        }, 500)
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
                    for (tag in allTagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.add(tag)
                            Log.d("chipList", chipList.toString())
                        }
                    }
                } else {
                    for (tag in allTagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.remove(tag)
                            Log.d("chipList", chipList.toString())
                        }
                    }
                }
            }
        }
    }

    private fun updateChips(allTags: List<TagEntity>) {
        Log.d("contactTagsList", contactTagsList.toString())
        val chipGroup = binding.tagChipGroup
        chipGroup.removeAllViewsInLayout()
        for (tag in allTags) {
            val chip = layoutInflater.inflate(R.layout.layout_chip_choice, chipGroup, false) as Chip
            chip.text = (tag.name)
            chip.id = (tag.tagId.toInt())
            if (tag in contactTagsList) {
                Log.d("tag exists", tag.toString())
                chip.isChecked = true
            }
            chipGroup.addView(chip)
        }
        addListenerOnButtonClick()
    }

    private fun updateContactTags() {
        viewModel.deleteAllTagCrossRefByCardId(contactId)
        Log.d("+++", "All tag crossrefs deleted")
        for (tag in chipList) {
            val cardTagCrossRef = CardTagCrossRef(
                cardId = contactId,
                tagId = tag.tagId
            )
            viewModel.insertCardTagCrossRef(cardTagCrossRef)
        }
        Log.d("+++", "All new tag crossrefs added")
    }


}