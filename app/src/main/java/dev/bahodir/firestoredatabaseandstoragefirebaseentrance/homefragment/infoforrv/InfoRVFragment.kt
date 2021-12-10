package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.infoforrv

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentInfoRVBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users.Users
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "users"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoRVFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoRVFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var users: Users? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users = it.getSerializable(ARG_PARAM) as Users?
            //param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentInfoRVBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private val TAG = "InfoRVFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoRVBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()

        Glide.with(requireContext()).load(users?.photo).into(binding.imageLoad)
        binding.textSet.text = users?.info
        binding.year.text = "(${users?.from}-${users?.to})"
        binding.name.text = users?.name

        binding.search.setOnClickListener {
            binding.search.visibility = View.INVISIBLE
            binding.saved.visibility = View.INVISIBLE
            binding.back.visibility = View.INVISIBLE

            binding.searchEdit.visibility = View.VISIBLE
            binding.clearText.visibility = View.VISIBLE
        }
        binding.clearText.setOnClickListener {
            binding.search.visibility = View.VISIBLE
            binding.saved.visibility = View.VISIBLE
            binding.back.visibility = View.VISIBLE

            binding.searchEdit.visibility = View.GONE
            binding.clearText.visibility = View.GONE
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.textSet.text = "${users?.info}"
                binding.textSet .setTextToHighlight("${p0?.toString()}")
                binding.textSet.setTextHighlightColor("#EBF868")
                binding.textSet.setCaseInsensitive(true)
                binding.textSet.highlight()
            }
        })
        if (users?.isLike == true) {
            binding.saved.setBackgroundResource(R.drawable.circle_green)
            binding.saved.setImageResource(R.drawable.saved_white)
        }
        else {
            binding.saved.setBackgroundResource(R.drawable.circle_white)
            binding.saved.setImageResource(R.drawable.saved_black)
        }

        binding.saved.setOnClickListener {
            if (users?.isLike == true) {
                users?.isLike = false
                binding.saved.setBackgroundResource(R.drawable.circle_white)
                binding.saved.setImageResource(R.drawable.saved_black)
            }
            else {
                users?.isLike = true
                binding.saved.setBackgroundResource(R.drawable.circle_green)
                binding.saved.setImageResource(R.drawable.saved_white)
            }
            firestore
                .collection("users")
                .document(users?.id.toString())
                .set(users!!)
                .addOnSuccessListener {
                    Log.d(TAG, "onCreateView: Success")
                }
                .addOnFailureListener {
                    Log.d(TAG, "onCreateView: Error")
                }
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
         * @param users Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoRVFragment.
         */
        // TODO: Rename and change types and number of parameters
        /*@JvmStatic
        fun newInstance(users: String, param2: String) =
            InfoRVFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, users)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }
}