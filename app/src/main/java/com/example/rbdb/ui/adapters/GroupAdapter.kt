package com.example.rbdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.ui.dataclasses.Group

class GroupAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = ArrayList<Group>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GroupViewHolder).onBind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Group>) {
        this.data.clear()
        this.data.addAll(data)
    }

    inner class GroupViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group, parent, false)
    ) {
        val tvGroupName: TextView = itemView.findViewById(R.id.group_name)

        fun onBind(
            groupData: Group,
            position: Int
        ) {
            tvGroupName.text = groupData.name
        }
    }
}