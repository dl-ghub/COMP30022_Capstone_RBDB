package com.example.rbdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R

class ContactAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContactViewHolder).onBind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Contact>) {
        this.data.clear()
        this.data.addAll(data)
    }

    inner class ContactViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contact, parent, false)
    ) {

        val ivContactAvatar: ImageView = itemView.findViewById(R.id.contact_avatar)
        val tvContactName: TextView = itemView.findViewById(R.id.contact_name)
        val tvContactCompany: TextView = itemView.findViewById(R.id.contact_company)
        val tvContactPhone: TextView = itemView.findViewById(R.id.contact_phone)

        fun onBind(
            contactData: Contact,
            position: Int
        ) {
            ivContactAvatar.setImageResource(contactData.avatar)
            tvContactName.text = contactData.name
            tvContactCompany.text = contactData.company
            tvContactPhone.text = contactData.phone
        }
    }
}