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
import com.example.rbdb.R
import com.example.rbdb.databinding.ActivityGroupBinding
import com.example.rbdb.ui.arch.AppViewModel

class GroupDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupBinding
    private val viewModel: AppViewModel by viewModels()
    private var groupId: Long = 0

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

        val groupTitle = intent.getStringExtra("group_name")
        groupId = intent.getLongExtra("group_id", -1)
        supportActionBar?.title = groupTitle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_group_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit -> {
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
        R.id.delete -> {
            Toast.makeText(this, "delete pressed", Toast.LENGTH_SHORT).show()
            true
// ***Add this in when the delete button query for the database is done
//            val builder = AlertDialog.Builder(this)
//            builder.setMessage(R.string.confirm_delete_group)
//            builder.setPositiveButton("Delete") { _, _ ->
//                deleteGroup(groupId)
//
//                // Return to Homepage
//                val intent = Intent(this, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
//
//                finish()
//            }
//
//            builder.setNegativeButton("Cancel") { _, _ -> }
//
//            val alertDialog: AlertDialog = builder.create()
//            alertDialog.show()
//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                .setTextColor(resources.getColor(R.color.warningRed))
//
//            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

//    private fun deleteGroup(groupId: Long) {
//        Log.d("groupId to be deleted", groupId.toString())
//        viewModel.deleteCardAndCrossRefByCardId(groupId)
//    }
}