package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val view = binding.root

        // initialise view model/database for this fragment
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
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


        val fab = binding.groupFab
        fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage(R.string.new_group_name)
            val inflater =
                requireActivity().layoutInflater.inflate(R.layout.view_holder_new_dialog, null)
            builder.setView(inflater)
            val input = inflater.findViewById<View>(R.id.new_name) as EditText

            // Send the name to the database to create a new group
            builder.setPositiveButton("Ok") {_, _ ->
                val groupId = saveGroupToDatabase(
                    input.text.toString().trim()
                )
                Log.d("groupId gFrag", groupId.toString())
                viewModel.getAllLists().observe(requireActivity(), { groups ->
                    adapter.swapData(groups)
                    groupList = groups
                })
                /*val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    val intent = Intent(requireActivity(), EditGroupActivity::class.java).apply {
                        putExtra("group_id", groupId)

                        putExtra("group_name", newGroupName)
                    }
                    startActivity(intent)
                },1000)*/

            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
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

    private fun saveGroupToDatabase(groupName: String) {
        // TODO Check for Empty inputs
        //Log.d("groupName", groupName)
        val listEntity = ListEntity(
            name = groupName
        )
        //var listId: Long = 0
        val observer = Observer<Long> { id->
            val intent = Intent(requireActivity(), EditGroupActivity::class.java).apply {
                putExtra("group_id", id)

                putExtra("group_name", listEntity.name)
            }
            startActivity(intent)
        }
        viewModel.insertList(listEntity).observe(requireActivity(),observer)
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