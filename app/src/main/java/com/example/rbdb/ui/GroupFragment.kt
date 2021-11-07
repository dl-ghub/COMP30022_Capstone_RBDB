package com.example.rbdb.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON1
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.ListEntity
import com.example.rbdb.databinding.FragmentGroupBinding
import com.example.rbdb.ui.adapters.GroupAdapter
import com.example.rbdb.ui.adapters.GroupCardInterface
import com.example.rbdb.ui.arch.AppViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment(), GroupCardInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var groupList: List<ListEntity>
    private lateinit var viewModel: AppViewModel
    private lateinit var adapter: GroupAdapter
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView: View
    private lateinit var newGroupTextField: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val view = binding.root

        // initialise view model/database for this fragment
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        viewModel.init(AppDatabase.getDatabase(requireActivity()))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView implementation
        val recyclerView: RecyclerView = binding.rvGroups

        adapter = GroupAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        viewModel.getAllLists().observe(requireActivity(), { groups ->
            adapter.swapData(groups)
            groupList = groups
        })

        // Set up new group FAB
        val fabAddGroup = binding.fabAddGroup
        fabAddGroup.setOnClickListener(View.OnClickListener {
            // Inflate custom alert dialog view
            customAlertDialogView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.view_holder_edit_text_dialog, null, false)

            // Launching the custom alert dialog
            launchCustomAlertDialog()
        })

        binding.rvGroups.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && fabAddGroup.isExtended) {
                    fabAddGroup.shrink()
                }

                if (dy < -10 && !fabAddGroup.isExtended) {
                    fabAddGroup.extend()
                }

                if (!binding.rvGroups.canScrollVertically(-1)) {
                    fabAddGroup.extend()
                }
            }
        })
    }


    /* Access GroupCardInterface for recycler view item navigation */
    override fun onGroupCardClick(position: Int) {
        val group = groupList[position]
        val intent = Intent(this.requireActivity(), GroupDetailActivity::class.java).apply {
            putExtra("group_name", group.name)
            putExtra("group_id", group.listId)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLists().observe(requireActivity(), { groups ->
            adapter.swapData(groups)
            groupList = groups
        })
    }

    private fun launchCustomAlertDialog() {
        newGroupTextField = customAlertDialogView.findViewById(R.id.new_group_name)

        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("Enter the new group name")
            .setPositiveButton("Ok") { dialog, _ ->
                val newGroupName = newGroupTextField.editText?.text.toString().trim()
                // TODO Null checking doesn't work yet
                if (newGroupName.isEmpty()) {
                    newGroupTextField.error = "You need to enter a name"
                } else {
                    saveGroupToDatabase(newGroupName)
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun displayMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun saveGroupToDatabase(groupName: String) {
        //Log.d("groupName", groupName)
        val listEntity = ListEntity(
            name = groupName
        )
        //var listId: Long = 0
        val observer = Observer<Long> { id ->
            val intent = Intent(requireActivity(), EditGroupActivity::class.java).apply {
                putExtra("group_id", id)
                putExtra("group_name", listEntity.name)
            }
            startActivity(intent)
        }
        viewModel.insertList(listEntity).observe(requireActivity(), observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GroupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GroupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance() =
            GroupFragment()
    }
}