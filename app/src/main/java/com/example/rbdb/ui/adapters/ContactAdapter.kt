package com.example.rbdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.rbdb.database.model.CardEntity
import com.example.rbdb.databinding.ViewHolderContactBinding
import com.example.rbdb.ui.SearchActivity
import com.example.rbdb.ui.dataclasses.Contact
import de.hdodenhof.circleimageview.CircleImageView
import com.example.rbdb.ui.ContactFragment

class ContactAdapter(
    private val data: MutableList<CardEntity>,
    private val contactCardInterface: ContactCardInterface
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ViewHolderContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(data[position], contactCardInterface)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(data: List<CardEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    inner class ContactViewHolder(private val binding: ViewHolderContactBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun onBind(contactData: CardEntity, contactCardInterface: ContactCardInterface) {
            val generator: ColorGenerator = ColorGenerator.MATERIAL
            val firstInitial = contactData.name[0].toString() // used for avatar letter
            val whiteSpaceIndex = contactData.name.indexOf(" ")
            val secondInitial =
                contactData.name[whiteSpaceIndex + 1].toString() // used for avatar colour
            val drawable: TextDrawable =
                TextDrawable.builder().buildRound(firstInitial, generator.getColor(secondInitial))

            binding.contactAvatar.setImageDrawable(drawable)
            binding.contactName.text = contactData.name
            binding.contactCompany.text = contactData.business
            binding.contactPhone.text = contactData.phone

            binding.contactCard.setOnClickListener {
                contactCardInterface.onContactCardClick(absoluteAdapterPosition)
            }
        }
    }
}