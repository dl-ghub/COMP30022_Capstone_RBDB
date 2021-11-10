package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.ActivityGroupBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.example.rbdb.ui.arch.AppViewModel
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class GroupDetailActivity : AppCompatActivity(), ContactCardInterface {
    private lateinit var binding: ActivityGroupBinding
    private val viewModel: AppViewModel by viewModels()
    private var groupId: Long = 0
    private lateinit var groupTitle: String
    private lateinit var adapter: ContactAdapter
    private lateinit var contactList: List<CardEntity>
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View
    private lateinit var newGroupTextField: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.init(AppDatabase.getDatabase(this))

        groupTitle = intent.getStringExtra("group_name").toString()
        groupId = intent.getLongExtra("group_id", -1)

        val recyclerView: RecyclerView = binding.rvContacts

        adapter = ContactAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        val observerContact = Observer<List<CardEntity>> { contacts ->
            val contactsSorted = contacts.sortedBy{ it.firstName }
            adapter.swapData(contactsSorted)
            contactList = contactsSorted
        }
        viewModel.getCardsInList(groupId).observe(this, observerContact)

        supportActionBar?.title = groupTitle

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(
            this,
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
        )
    }

    override fun onContactCardClick(position: Int) {
        val contact = contactList[position]
        val intent = Intent(this, ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_group_menu, menu)
        val item = menu?.findItem(R.id.delete_g)
        val s = SpannableString(resources.getString(R.string.delete_group))
        val color = ContextCompat.getColor(this, R.color.light_error)
        s.setSpan(ForegroundColorSpan(color), 0, s.length, 0)
        item!!.title = s
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.change_gName -> {
            customAlertDialogView = LayoutInflater.from(this)
                .inflate(R.layout.view_holder_edit_text_dialog, null, false)
            launchCustomAlertDialog()
            true
        }
        R.id.search -> {
            Toast.makeText(this, "search pressed", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.edit_gMembers -> {
//            Toast.makeText(this, "edit group members pressed", Toast.LENGTH_SHORT).show()
            // Navigate to EditGroupActivity
            val intent = Intent(this, EditGroupActivity::class.java).apply {
                putExtra("group_id", groupId)
                putExtra("group_name", groupTitle)
            }
            startActivity(intent)

            true
        }
        R.id.delete_g -> {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.confirm_delete_group)
            builder.setPositiveButton("Delete") { _, _ ->
                deleteGroup(groupId)

                // Return to Homepage
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    finish()
                }, 300)
            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.light_error))

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val observerContact = Observer<List<CardEntity>> { contacts ->
            val contactsSorted = contacts.sortedBy { it.firstName }
            adapter.swapData(contactsSorted)
            contactList = contactsSorted
        }
        viewModel.getCardsInList(groupId).observe(this, observerContact)
    }

    private fun launchCustomAlertDialog() {
        newGroupTextField = customAlertDialogView.findViewById(R.id.new_group_name)

        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("Enter the new group name")
            .setPositiveButton("Ok") { dialog, _ ->
                val newGroupName = newGroupTextField.editText?.text.toString().trim()

                if (newGroupName.isEmpty()) {
                    newGroupTextField.error = "You need to enter a name"
                } else {
                    updateListName(newGroupName, groupId)
                    supportActionBar?.title = newGroupName
                    overridePendingTransition(0, 0)
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteGroup(groupId: Long) {
        viewModel.deleteListAndCrossRefByListId(groupId)
    }

    private fun updateListName(name: String, groupId: Long) {
        viewModel.updateListName(name, groupId)
    }
}