package com.example.rbdb

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.topAppBar)
        //setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    // Handle search icon press
                    Toast.makeText(this, "search bar pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    Toast.makeText(this, "more button pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        val tabLayout = findViewById<TabLayout>(R.id.TabLayout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
        val viewPager = findViewById<ViewPager2>(R.id.pager)

        class FragmentAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){
            override fun getItemCount(): Int {

                return 3
            }

            override fun createFragment(position: Int): Fragment {

                return when(position){
                    0->ContactFragment.newInstance()
                    1->GroupFragment.newInstance()
                    2->TagFragment.newInstance()
                    else -> ContactFragment.newInstance()
                }
            }

        }
        viewPager.adapter = FragmentAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "contact"

                }
                1 -> {
                    tab.text = "group"

                }
                2->{
                    tab.text = "tags"
                }

            }
        }.attach()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.search)
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is expanded", Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Toast.makeText(this@MainActivity, "Search is collapsed", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        val searchItem = menu!!.findItem(R.id.search)
        var searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = "Search here..."

        return true
    }*/
}