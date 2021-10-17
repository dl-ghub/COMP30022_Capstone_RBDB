package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.FragmentContactBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.example.rbdb.ui.arch.AppViewModel
import com.example.rbdb.ui.dataclasses.Contact

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val HOME_BOOL = "home boolean"
private const val GROUP_ID = "group id"


/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment(), ContactCardInterface {
    // TODO: Rename and change types of parameters
    private var home: Boolean = false
    private var groupId: Long? = null
    //private lateinit var navHostFragment: NavHostFragment
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactList: List<CardEntity>
    private lateinit var viewModel: AppViewModel
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            home = it.getBoolean(HOME_BOOL)
            groupId = it.getLong(GROUP_ID)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root
//        val button = view.findViewById<Button>(R.id.buttonContact)
//        //button.setOnClickListener { findNavController().navigate(R.id.action_contactFragment_to_contactDetailActivity) }
//        button.setOnClickListener{
//            //Toast.makeText(this.requireActivity(), "Button pressed", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this.requireActivity(), ContactDetailActivity::class.java)
//            intent.putExtra("contactName",view.findViewById<Button>(R.id.buttonContact).text)
//            requireActivity().startActivity(intent)
//        }

        // initialise viewmodel/database for this fragment
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        viewModel.init(AppDatabase.getDatabase(requireActivity()))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // RecyclerView implementation
        val recyclerView: RecyclerView = binding.rvContacts

        adapter = ContactAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        val observerContact = Observer<List<CardEntity>> {contacts ->
            adapter.swapData(contacts)
            contactList = contacts  }

        if (home){
            viewModel.getAllCards().observe(requireActivity(), observerContact)
        }
        else{
            if (groupId != null) {
                viewModel.getCardsInList(groupId!!).observe(requireActivity(),observerContact)
            }
        }

        val fab = binding.contactFab
        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Add contact button clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()*/
            val intent = Intent(this.requireActivity(), NewContactActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }


    // Recyclerview item onclick navigation. Pass all required contact information in intent
    override fun onContactCardClick(position: Int) {
        val contact = contactList[position]
        val intent = Intent(this.requireActivity(), ContactDetailActivity::class.java).apply {
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

    override fun onResume() {
        super.onResume()
        val observerContact = Observer<List<CardEntity>> {contacts ->
            adapter.swapData(contacts)
            contactList = contacts  }

        if (home){
            viewModel.getAllCards().observe(requireActivity(), observerContact)
        }
        else{
            if (groupId != null) {
                viewModel.getCardsInList(groupId!!).observe(requireActivity(),observerContact)
            }
        }
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
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(groupId: Long) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(HOME_BOOL, false)
                    putLong(GROUP_ID, groupId)
                }
            }
        fun newInstance() =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(HOME_BOOL, true)
                    putLong(GROUP_ID, -1)
                }
            }
    }
}