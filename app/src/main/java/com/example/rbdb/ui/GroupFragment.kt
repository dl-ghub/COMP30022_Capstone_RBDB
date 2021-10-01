package com.example.rbdb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.databinding.FragmentContactBinding
import com.example.rbdb.databinding.FragmentGroupBinding
import com.example.rbdb.ui.adapters.GroupAdapter
import com.example.rbdb.ui.dataclasses.Group
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentGroupBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = binding.groupFab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Add group button clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
        // Recyclerview Implementation (GROUPS PAGE)
        // Dummy data. Eventually will need to retrieve this (in a similar format) from DB.
        val dataForAdapter = listOf(
            Group(1, "Tech. Companies"),
            Group(2, "Melbourne"),
            Group(3, "Group 3"),
            Group(4, "Group 4"),
            Group(5, "Group 5")
        )

        val recyclerView: RecyclerView = binding.rvGroups
        val groupAdapter = GroupAdapter()
        groupAdapter.setData(dataForAdapter)
        recyclerView.adapter = groupAdapter
        groupAdapter.notifyDataSetChanged()
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