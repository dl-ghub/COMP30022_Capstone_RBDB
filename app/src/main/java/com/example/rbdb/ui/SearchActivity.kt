package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rbdb.ui.arch.AppViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.SearchActivityBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.marginRight


class SearchActivity : AppCompatActivity(), ContactCardInterface {
    private lateinit var binding: SearchActivityBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var searchList: List<CardEntity>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val selectedSearchesArray = arrayOf("Name", "Business", "Date Added", "Phone", "Email", "Description")
    private val checkSearchesArray = booleanArrayOf(false, false, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recycler = binding.rvSearchResults
        adapter = ContactAdapter(mutableListOf(), this)
        recycler.adapter = adapter

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                displaySearch(s.toString())
            }
        })

        viewModel.init(AppDatabase.getDatabase(this))
    }

    fun displaySearch(input : String){
        Log.d("input", input)
        viewModel.getCardsByName(input).observe(this, { contacts ->
            adapter.swapData(contacts)
            searchList = contacts
        })
        adapter.notifyDataSetChanged()
    }

    // Recyclerview item onclick navigation. Pass all required contact information in intent
    override fun onContactCardClick(position: Int) {
        val contact = searchList[position]
        val intent = Intent(this, ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
            putExtra("contact_name", contact.name)
            putExtra("contact_business", contact.business)
            putExtra("contact_dateAdded", contact.dateAdded)
            putExtra("contact_phone", contact.phone)
            putExtra("contact_email", contact.email)
            putExtra("contact_description", contact.description)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.more -> {
            Toast.makeText(this, "more button pressed", Toast.LENGTH_SHORT).show()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Search Fields")
            builder.setMultiChoiceItems(selectedSearchesArray, checkSearchesArray, ) { _, which, isChecked ->
                checkSearchesArray[which] = isChecked
            }
            builder.setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }
}