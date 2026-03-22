package com.croptrack.farmerfriend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> Home()
            1 -> Climate()
            2 -> Detect()
            3 -> RentFragment()
            else -> Home()
        }
    }
}
