package com.example.rbdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.CardListCrossRef
import com.example.rbdb.databinding.ActivityEditGroupBinding
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.example.rbdb.ui.adapters.EditGroupContactsAdapter
import com.example.rbdb.ui.arch.AppViewModel

class EditGroupActivity : AppCompatActivity(), ContactCardInterface {
    private lateinit var binding: ActivityEditGroupBinding
    private val viewModel: AppViewModel by viewModels()
    private var groupId: Long = 0
    private lateinit var adapter: EditGroupContactsAdapter
    private lateinit var allContactsList: List<CardEntity>
    private lateinit var selectedContactIdsList: MutableList<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.init(AppDatabase.getDatabase(this))

        val groupTitle = intent.getStringExtra("group_name")
        supportActionBar?.title = "Edit $groupTitle"

        groupId = intent.getLongExtra("group_id", -1)

        // Identify contacts belonging to this group
        selectedContactIdsList = mutableListOf()
        val groupContactEntities = viewModel.getCardsInList(groupId).value
        if (groupContactEntities != null) {
            for (contact in groupContactEntities) {
                selectedContactIdsList.add(contact.cardId)
                Log.d("Contact in $groupId", contact.name)
            }
        }

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.rvContacts
        adapter = EditGroupContactsAdapter(mutableListOf(), selectedContactIdsList, this)
        recyclerView.adapter = adapter

        // Retrieve All Contacts (Not just ones in group)
        val observerAllContacts = Observer<List<CardEntity>> { contacts ->
            adapter.swapData(contacts)
            allContactsList = contacts
        }
        viewModel.getAllCards().observe(this, observerAllContacts)

        // Set up Save Changes Button
        binding.btnSaveChanges.setOnClickListener {
            updateGroupContacts(groupId, selectedContactIdsList)
        }
    adapter.swapSelectedContacts(selectedContactIdsList)

    }

    // NOT WORKING YET
    override fun onContactCardClick(position: Int) {
        if (allContactsList[position].cardId in selectedContactIdsList) {
            selectedContactIdsList.remove(allContactsList[position].cardId)
            adapter.swapSelectedContacts(selectedContactIdsList)
            Log.d("selected Contacts", selectedContactIdsList.toString())
        } else {
            selectedContactIdsList.add(allContactsList[position].cardId )
            adapter.swapSelectedContacts(selectedContactIdsList)
            Log.d("selected Contacts", selectedContactIdsList.toString())
        }
    }


    /**
     * Updates the contacts belonging to a particular group.
     * groupId: ID of the group we want to update
     * contactIds: List of contact's Ids that we want the group to update with.
     */
    private fun updateGroupContacts(groupId: Long, selectedContactIdsList: MutableList<Long>) {

        /* Start by deleting all crossRefs for the desired list */
        viewModel.deleteAllCrossRefByListId(groupId)


        /* Iterate through each contact in contactIds and add crossRef to list */
        for (contactId in selectedContactIdsList) {
            val crossRef = CardListCrossRef(contactId, groupId)
            viewModel.insertCardToList(crossRef)
            Log.d("Successfully added to $groupId", contactId.toString())
        }

        /* Navigate back to appropriate GroupDetailActivity*/
        this.onBackPressed()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_group_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.search -> {
            Toast.makeText(this, "search bar pressed", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return true
    }

}