package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.BackLayoutBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentAdiblarBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.searchfragment.SearchFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.vpforadiblar.VPForAdiblarFr

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdiblarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdiblarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentAdiblarBinding? = null
    private val binding get() = _binding!!
    private lateinit var vpForAdiblarFr: VPForAdiblarFr

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdiblarBinding.inflate(inflater, container, false)

        vpForAdiblarFr = VPForAdiblarFr(requireActivity())
        binding.viewPager.adapter = vpForAdiblarFr


        val arr = arrayOf("Mumtoz adabiyoti", "O'zbek adabiyoti", "Jahon adabiyoti")

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            var bind = BackLayoutBinding.inflate(layoutInflater)

            tab.customView = bind.root
            bind.texts.text = arr[position]

            if (position == 0) {
                bind.backs.visibility = View.VISIBLE
                bind.texts.setTextColor(Color.WHITE)
            }
            else {
                bind.backs.visibility = View.INVISIBLE
                bind.texts.setTextColor(Color.BLACK)
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var bind = BackLayoutBinding.bind(tab?.customView!!)
                bind.backs.visibility = View.VISIBLE
                bind.texts.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var bind = BackLayoutBinding.bind(tab?.customView!!)
                bind.backs.visibility = View.INVISIBLE
                bind.texts.setTextColor(Color.BLACK)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdiblarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdiblarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}