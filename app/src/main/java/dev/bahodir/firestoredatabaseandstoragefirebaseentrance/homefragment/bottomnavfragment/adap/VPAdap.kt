package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.adap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.AdiblarFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.SaqlanganlarFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.SozlamalarFragment

class VPAdap(var fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AdiblarFragment()
            1 -> SaqlanganlarFragment()
            else -> SozlamalarFragment()
        }
    }
}