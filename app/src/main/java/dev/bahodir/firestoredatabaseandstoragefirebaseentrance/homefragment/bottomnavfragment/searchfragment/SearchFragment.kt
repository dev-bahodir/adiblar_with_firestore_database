package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.bottomnavfragment.searchfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentSearchBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.adapter.RVForToolBar
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users.Users
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
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

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var list: ArrayList<Users>
    private lateinit var rvForToolBar: RVForToolBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()

        list = ArrayList()
        rvForToolBar =
            RVForToolBar(requireContext(), list, object : RVForToolBar.OnItemTouchListener {
                override fun onItemClick(users: Users, position: Int, view: View) {

                }

                override fun savedClick(users: Users, position: Int, view: View) {

                }

            })
        binding.recycler.adapter = rvForToolBar
        firestore
            .collection("users")
            .get()
            .addOnSuccessListener {
                list.clear()
                it.forEach { res ->
                    val users = res.toObject(Users::class.java)
                    list.add(users)
                }
                rvForToolBar.notifyDataSetChanged()
            }
            .addOnFailureListener {

            }

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                firestore
                    .collection("users")
                    .get()
                    .addOnSuccessListener {
                        list.clear()
                        it.forEach { res ->
                            val users = res.toObject(Users::class.java)
                            if (users.name?.contains("${p0?.toString()}") == true)
                                list.add(users)
                        }
                        rvForToolBar.notifyDataSetChanged()
                    }
                    .addOnFailureListener {

                    }
            }

        })

        binding.clearText.setOnClickListener {
            binding.searchEdit.setText("")
            list.clear()
            rvForToolBar.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}