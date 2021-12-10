package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.vpforadiblar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.JahonAdabiyotiFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.MumtozAdabiyotiFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.OzbekAdabiyotiFragment

class VPForAdiblarFr(var fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MumtozAdabiyotiFragment()
            1 -> OzbekAdabiyotiFragment()
            else -> JahonAdabiyotiFragment()
        }
    }
}