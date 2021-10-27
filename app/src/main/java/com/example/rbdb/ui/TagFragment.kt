package com.example.rbdb.ui

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
import androidx.lifecycle.ViewModelProvider
import com.example.rbdb.database.AppDatabase
import com.example.rbdb.database.model.TagEntity
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
class TagFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTagBinding? = null
    private val binding get() = _binding!!
    private var tagsList: List<TagEntity> = mutableListOf()
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun addListenerOnButtonClick() {
        val chipGroup = binding.tagChipGroup
        val chipList = ArrayList<Chip>()
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    chipList.add(chip)
                } else {
                    chipList.remove(chip)
                }
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
            val result = StringBuilder().append("Selected Items:")
            for (chip in chipList) {
                if (chip.isChecked) {
                    result.append("\n" + chip.text.toString())
                }
            }
            Toast.makeText(view.context, result.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        val chipGroup = binding.tagChipGroup


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
