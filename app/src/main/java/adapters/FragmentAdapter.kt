package adapters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rbdb.ContactFragment
import com.example.rbdb.GroupFragment
import com.example.rbdb.TagFragment

class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0->{
                ContactFragment.newInstance()
            }
            1-> GroupFragment.newInstance()
            2-> TagFragment.newInstance()
            else -> ContactFragment.newInstance()
        }
    }

}