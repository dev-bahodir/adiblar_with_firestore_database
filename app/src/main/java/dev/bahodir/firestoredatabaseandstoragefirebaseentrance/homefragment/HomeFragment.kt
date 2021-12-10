package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.viewpager2.widget.ViewPager2
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentHomeBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.AdiblarFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.SaqlanganlarFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.SozlamalarFragment
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.adap.VPAdap
import me.ibrahimsn.lib.OnItemSelectedListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var vpAdap: VPAdap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        vpAdap = VPAdap(requireActivity())
        binding.frame.adapter = vpAdap

        binding.smooth.setOnItemSelectedListener {
            when (it) {
                0 -> { binding.frame.currentItem = 0}
                1 -> { binding.frame.currentItem = 1 }
                else -> { binding.frame.currentItem = 2 }
            }
        }

        binding.frame.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> { binding.smooth.itemActiveIndex = 0 }
                    1 -> { binding.smooth.itemActiveIndex = 1}
                    else -> { binding.smooth.itemActiveIndex = 2}
                }

                binding.smooth.itemActiveIndex = position
            }
        })
        binding.frame.isUserInputEnabled = false


        /*requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, AdiblarFragment( ))
            .commit()

        binding.smooth.setOnItemSelectedListener {
            when(it) {
                0 -> replace(AdiblarFragment())
                1 -> replace(SaqlanganlarFragment())
                2 -> replace(SozlamalarFragment())
            }
            return@setOnItemSelectedListener
        }*/


        return binding.root
    }
    private fun replace(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}