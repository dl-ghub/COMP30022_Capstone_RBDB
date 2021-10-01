package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.databinding.FragmentContactBinding
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.dataclasses.Contact

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private lateinit var navHostFragment: NavHostFragment
    private var _binding: FragmentContactBinding? = null
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

        // Recyclerview Implementation (CONTACTS PAGE)
        // Dummy data. Eventually will need to retrieve this (in a similar format) from DB.

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            val intent = Intent(this.requireActivity(), NewContactActivity::class.java)
            requireActivity().startActivity(intent)
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
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        fun newInstance() =
            ContactFragment()
    }
}