package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.rbdb.ui.arch.AppViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.SearchActivityBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SearchActivity : AppCompatActivity(), ContactCardInterface {
    private lateinit var binding: SearchActivityBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var searchList: List<CardEntity>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val selectedSearchesArray =
        arrayOf("Name", "Position", "Business", "Date Added", "Phone", "Email", "Description")
    private val adaptedArray = arrayOf(
        "firstName",
        "lastName",
        "position",
        "business",
        "dateAdded",
        "phone",
        "email",
        "description"
    )
    private val checkSearchesArray = booleanArrayOf(true, true, true, true, true, true, true)

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

    fun displaySearch(input: String) {
        Log.d("input", input)
        Log.d("list", queryList().toString())
        viewModel.getCardByKeywordInSelectedColumns(input, queryList()).observe(this, { contacts ->
            adapter.swapData(contacts)
            searchList = contacts
        })
        adapter.notifyDataSetChanged()
    }

    private fun queryList(): List<String> {
        val returnList: MutableList<String> = mutableListOf()
        for (i in selectedSearchesArray.indices) {
            if (checkSearchesArray[i]) {
                returnList.add(adaptedArray[i])
            }
        }
        return returnList
    }

    // Recyclerview item onclick navigation. Pass all required contact information in intent
    override fun onContactCardClick(position: Int) {
        val contact = searchList[position]
        val intent = Intent(this, ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.more -> {
            var trueCount = checkSearchesArray.count { it }

            val dialog = MaterialAlertDialogBuilder(
                this,
                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
            )
                .setTitle("Select Search Fields")
                .setMultiChoiceItems(
                    selectedSearchesArray,
                    checkSearchesArray
                ) { _, which, checked ->
                    checkSearchesArray[which] = checked
                    trueCount = checkSearchesArray.count { it }
                    Log.d("true count", trueCount.toString())
                }
                .setPositiveButton("OK") { dialog, _ ->
                    if (trueCount < 1) {
//                        val toast =
//                            Toast.makeText(this, "Select at least 1 field", Toast.LENGTH_SHORT)
//                        toast.show()
                    } else {
                        dialog.dismiss()
                    }

                }
                .create()

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ ->
                if (trueCount < 1) {
                    val toast = Toast.makeText(this, "Select at least 1 field", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    dialog.dismiss()
                }
            }

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