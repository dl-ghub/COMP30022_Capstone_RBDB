package com.example.rbdb.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rbdb.ui.ContactFragment
import com.example.rbdb.ui.GroupFragment
import com.example.rbdb.ui.TagFragment

class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0-> ContactFragment.newInstance()
            1-> GroupFragment.newInstance()
            2-> TagFragment.newInstance()
            else -> ContactFragment.newInstance()
        }
    }

}