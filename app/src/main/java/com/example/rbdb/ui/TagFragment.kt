package com.example.rbdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rbdb.R
import com.example.rbdb.databinding.FragmentTagBinding
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.database.model.TagEntity
import com.example.rbdb.ui.adapters.ContactAdapter
import com.example.rbdb.ui.adapters.ContactCardInterface
import com.example.rbdb.ui.arch.AppViewModel
import com.google.android.material.chip.Chip
import java.lang.StringBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TagFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TagFragment : Fragment(), ContactCardInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTagBinding? = null
    private val binding get() = _binding!!
    private var tagsList: List<TagEntity> = mutableListOf()
    private lateinit var viewModel: AppViewModel
    private lateinit var searchList: List<CardEntity>
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun addListenerOnButtonClick() {
        val chipGroup = binding.tagChipGroup
        val chipList = ArrayList<TagEntity>()
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    for (tag in tagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.add(tag)
                        }
                    }
                } else {
                    for (tag in tagsList) {
                        if (tag.tagId == chip.id.toLong()) {
                            chipList.remove(tag)
                        }
                    }
                }
                displaySearch(chipList)
            }
        }

        binding.tagFabAdd.setOnClickListener { view ->
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage(R.string.new_tag_name)
            val inflater =
                requireActivity().layoutInflater.inflate(R.layout.view_holder_new_dialog, null)
            builder.setView(inflater)
            val input = inflater.findViewById<View>(R.id.new_name) as EditText

            // Send the name to the database to create a new tag
            builder.setPositiveButton("Ok") { _, _ ->
                saveTagToDatabase(
                    input.text.toString().trim()
                )
                viewModel.getAllTags().observe(requireActivity(), { tags ->
                    tagsList = tags
                    updateChips(tags)
                })
            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        binding.tagFabDelete.setOnClickListener { view ->
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage(R.string.confirm_delete_tags)

            builder.setPositiveButton("Delete") { _, _ ->
                deleteTags(chipList)
                viewModel.getAllTags().observe(requireActivity(), { tags ->
                    tagsList = tags
                    updateChips(tags)
                })
            }

            builder.setNegativeButton("Cancel") { _, _ -> }

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(requireActivity(),R.color.warningRed))

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTagBinding.inflate(inflater, container, false)
        val view = binding.root

        // initialise viewmodel/database for this fragment
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        viewModel.init(AppDatabase.getDatabase(requireActivity()))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ChipGroup implementation
        viewModel.getAllTags().observe(requireActivity(), { tags ->
            tagsList = tags
            updateChips(tags)
        })

        // RecyclerView implementation for search results
        recycler = binding.rvContacts
        adapter = ContactAdapter(mutableListOf(), this)
        recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateChips(tags: List<TagEntity>) {
        val chipGroup = binding.tagChipGroup
        chipGroup.removeAllViewsInLayout()
        for (tag in tags) {
            val chip = layoutInflater.inflate(R.layout.layout_chip_choice, chipGroup, false) as Chip
            chip.text = (tag.name)
            chip.id = (tag.tagId.toInt())
            chipGroup.addView(chip)
        }
        addListenerOnButtonClick()
    }

    private fun saveTagToDatabase(tagName: String) {
        // TODO Check for Empty inputs
        Log.d("tagName", tagName)
        val tagEntity = TagEntity(
            name = tagName
        )
        viewModel.insertTag(tagEntity)
    }

    private fun displaySearch(input : List<TagEntity>) {
        val tagIdList: ArrayList<Long> = ArrayList()
        for (tag in input) {
            tagIdList.add(tag.tagId)
        }
        viewModel.getCardByTagIds(tagIdList).observe(requireActivity(), { contacts ->
            adapter.swapData(contacts)
            searchList = contacts
        })
        adapter.notifyDataSetChanged()
    }

    private fun deleteTags(tags: List<TagEntity>) {
        for (tag in tags) {
            viewModel.deleteTagAndCrossRefByTagId(tag.tagId)
        }
        displaySearch(listOf())
    }

    override fun onContactCardClick(position: Int) {
        val contact = searchList[position]
        val intent = Intent(this.requireActivity(), ContactDetailActivity::class.java).apply {
            putExtra("contact_id", contact.cardId)
        }
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TagFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TagFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance() =
            TagFragment()
    }
}
