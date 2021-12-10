package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentJahonAdabiyotiBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentMumtozAdabiyotiBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.toolbarfragment.adapter.RVForToolBar
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users.Users
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JahonAdabiyotiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JahonAdabiyotiFragment : Fragment() {
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
    private var _binding: FragmentJahonAdabiyotiBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var list: ArrayList<Users>
    private lateinit var rvForToolBar: RVForToolBar
    private val TAG = "JahonAdabiyotiFragment"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJahonAdabiyotiBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()

        list = ArrayList()
        rvForToolBar = RVForToolBar(requireContext(), list, object : RVForToolBar.OnItemTouchListener {
            override fun onItemClick(users: Users, position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("users", users)
                findNavController().navigate(R.id.action_homeFragment_to_infoRVFragment, bundle)
            }

            override fun savedClick(users: Users, position: Int, view: View) {
                firestore
                    .collection("users")
                    .document(users.id.toString())
                    .set(users)
                    .addOnSuccessListener {
                        Log.d(TAG, "onCreateView: Success")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "onCreateView: Error")
                    }
            }

        })
        binding.recycler.adapter = rvForToolBar

        firestore
            .collection("users")
            .get()
            .addOnSuccessListener {
                list.clear()
                it.forEach {res->
                    val users = res.toObject(Users::class.java)
                    if (users.type == "Jahon")
                        list.add(users)
                }
                rvForToolBar.notifyDataSetChanged()
            }
            .addOnFailureListener {

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
         * @return A new instance of fragment JahonAdabiyotiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JahonAdabiyotiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}