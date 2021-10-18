package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
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
import androidx.core.content.ContextCompat
import com.example.rbdb.database.model.ListEntity


class GroupDetailActivity : AppCompatActivity(), ContactCardInterface {
    private lateinit var binding: ActivityGroupBinding
    private val viewModel: AppViewModel by viewModels()
    private var groupId: Long = 0
    private lateinit var adapter: ContactAdapter
    private lateinit var contactList: List<CardEntity>

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

        val groupTitle = intent.getStringExtra("group_name")
        groupId = intent.getLongExtra("group_id", -1)
        val observerGroup = Observer<ListEntity> { group ->
            supportActionBar?.title = group.name
        }
        viewModel.getListById(groupId).observe(this, observerGroup)

        val recyclerView: RecyclerView = binding.rvContacts

        adapter = ContactAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        val observerContact = Observer<List<CardEntity>> { contacts ->
            adapter.swapData(contacts)
            contactList = contacts  }

        viewModel.getCardsInList(groupId).observe(this, observerContact)
    }
    override fun onContactCardClick(position: Int) {
        val contact = contactList[position]
        val intent = Intent(this, ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
            /*putExtra("contact_name", contact.name)
            putExtra("contact_business", contact.business)
            putExtra("contact_dateAdded", contact.dateAdded)
            putExtra("contact_phone", contact.phone)
            putExtra("contact_email", contact.email)
            putExtra("contact_description", contact.description)*/
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_group_menu, menu)
        val item = menu?.findItem(R.id.delete_g)
        val s = SpannableString(resources.getString(R.string.delete_group))
        val color = ContextCompat.getColor(this,R.color.warningRed)
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
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.new_group_name)
            val inflater = layoutInflater.inflate(R.layout.view_holder_new_dialog, null)
            builder.setView(inflater)
            val input = inflater.findViewById<View>(R.id.new_name) as EditText

            // Send the name to the database to create a new group (need to implement)
            builder.setPositiveButton("Ok"){ _, _ -> }
// ***Add functionality for changing the group's name here
            builder.setNegativeButton("Cancel"){_, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            true
        }
        R.id.search -> {
            Toast.makeText(this, "search pressed", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.edit_gMembers ->{
            Toast.makeText(this, "edit group members pressed", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.delete_g -> {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.confirm_delete_group)
            builder.setPositiveButton("Delete") { _, _ ->
                deleteGroup(groupId)

                // Return to Homepage
                this.onBackPressed()
            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.warningRed))

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun deleteGroup(groupId: Long) {
        Log.d("groupId to be deleted", groupId.toString())
        viewModel.deleteByListId(groupId)
    }
}