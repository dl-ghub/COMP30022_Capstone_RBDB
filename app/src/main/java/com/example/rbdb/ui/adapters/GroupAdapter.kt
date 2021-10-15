package com.example.rbdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.database.model.ListEntity
import com.example.rbdb.databinding.ViewHolderGroupBinding

class GroupAdapter(
    private val data: MutableList<ListEntity>,
    private val groupCardInterface: GroupCardInterface
) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding =
            ViewHolderGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.onBind(data[position], groupCardInterface)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(data: List<ListEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class GroupViewHolder(private val binding: ViewHolderGroupBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun onBind(groupData: ListEntity, groupCardInterface: GroupCardInterface) {
            binding.groupName.text = groupData.name
            binding.groupCard.setOnClickListener {
                groupCardInterface.onGroupCardClick(absoluteAdapterPosition)
            }
        }
    }
}