package com.example.rbdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rbdb.R
import com.example.rbdb.ui.dataclasses.Contact
import de.hdodenhof.circleimageview.CircleImageView

class ContactAdapter(
    private val data: ArrayList<Contact>,
    private val contactCardInterface: ContactCardInterface
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(data[position], contactCardInterface)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ContactViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contact, parent, false)
    ) {

        private val ivContactAvatar: CircleImageView = itemView.findViewById(R.id.contact_avatar)
        private val tvContactName: TextView = itemView.findViewById(R.id.contact_name)
        private val tvContactCompany: TextView = itemView.findViewById(R.id.contact_company)
        private val tvContactPhone: TextView = itemView.findViewById(R.id.contact_phone)
        private val contactCard: ConstraintLayout = itemView.findViewById(R.id.contact_card)

        fun onBind(contactData: Contact, contactCardInterface: ContactCardInterface) {
            ivContactAvatar.setImageResource(contactData.avatar)
            tvContactName.text = contactData.name
            tvContactCompany.text = contactData.company
            tvContactPhone.text = contactData.phone

            contactCard.setOnClickListener {
                contactCardInterface.onContactCardClick(absoluteAdapterPosition)
            }
        }
    }
}