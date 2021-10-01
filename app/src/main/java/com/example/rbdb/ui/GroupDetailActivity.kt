package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.databinding.ActivityGroupDetailBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.dataclasses.Contact

class GroupDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val dataForAdapter = listOf(
            Contact(R.drawable.arnold, "Arnold Schwarzenegger", "Paramount", "03 5357 2225"),
            Contact(R.drawable.arnold, "Barack Obama", "The White House", "03 5357 2225"),
            Contact(R.drawable.arnold, "Bill Gates", "Microsoft", "03 5357 2225"),
            Contact(R.drawable.arnold, "Chris Cuomo", "CNN", "03 5357 2225"),
            Contact(R.drawable.arnold, "Elon Musk", "Space X", "03 5357 2225"),
            Contact(R.drawable.arnold, "Jeff Bezos", "Amazon", "03 5357 2225")
        )
        val recyclerView: RecyclerView = binding.rvContacts
        val contactAdapter = ContactAdapter()
        contactAdapter.setData(dataForAdapter)
        recyclerView.adapter = contactAdapter
        contactAdapter.notifyDataSetChanged()
        val fab = binding.contactFab
        fab.setOnClickListener { view ->
            val intent = Intent(this, NewContactActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.search -> {
            Toast.makeText(this, "search bar pressed", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.more -> {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.new_group_name)

            val inflater = layoutInflater.inflate(R.layout.view_holder_new_group_dialog, null)
            builder.setView(inflater)
            val input = inflater.findViewById<View>(R.id.new_group_name) as EditText

            // Send the name to the database to create a new group (need to implement)
            builder.setPositiveButton("Ok"){ _, _ -> }

            builder.setNegativeButton("Cancel"){_, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}