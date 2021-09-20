import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rbdb.ContactFragment

class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    //private const val ARG_OBJECT = "object"
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = ContactFragment()
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            //putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }

}