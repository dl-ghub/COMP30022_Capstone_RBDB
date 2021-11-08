package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.FragmentContactBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.example.rbdb.ui.arch.AppViewModel

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
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root

        // initialise viewModel/database for this fragment
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        viewModel.init(AppDatabase.getDatabase(requireActivity()))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // RecyclerView implementation
        val recyclerView: RecyclerView = binding.rvContacts

        adapter = ContactAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        val observerContact = Observer<List<CardEntity>> { contacts ->
            adapter.swapData(contacts)
            contactList = contacts
        }

        viewModel.getAllCards().observe(requireActivity(), observerContact)


        // Set up new contact FAB
        val fabAddContact = binding.fabAddContact
        fabAddContact.setOnClickListener {
            val intent = Intent(this.requireActivity(), NewContactActivity::class.java)
            requireActivity().startActivity(intent)
        }

        binding.rvContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10 && fabAddContact.isExtended) {
                    fabAddContact.shrink()
                }

                if (dy < -10 && !fabAddContact.isExtended) {
                    fabAddContact.extend()
                }

                if (!binding.rvContacts.canScrollVertically(-1)) {
                    fabAddContact.extend()
                }
            }
        })

    }


    // Recyclerview item onclick navigation. Pass all required contact information in intent
    override fun onContactCardClick(position: Int) {
        val contact = contactList[position]
        val intent = Intent(this.requireActivity(), ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val observerContact = Observer<List<CardEntity>> { contacts ->
            adapter.swapData(contacts)
            contactList = contacts
        }

        if (home) {
            viewModel.getAllCards().observe(requireActivity(), observerContact)
        } else {
            Log.d("groupId", groupId.toString())
            if (groupId != null) {
                viewModel.getCardsInList(groupId!!).observe(requireActivity(), observerContact)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
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