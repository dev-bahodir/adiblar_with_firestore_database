package dev.bahodir.firestoredatabaseandstoragefirebaseentrance.homefragment.sozlamalarfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.R
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.databinding.FragmentAddBinding
import dev.bahodir.firestoredatabaseandstoragefirebaseentrance.users.Users
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
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

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var image: String? = null
    private lateinit var firestore: FirebaseFirestore
    private val TAG = "AddFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()

        var count1 = 0
        binding.adibRasmi.setOnClickListener {
            Dexter.withContext(requireContext())
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_LONG)
                            .show()
                        getImage.launch("image/*")
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Permission")
                            .setMessage("The task will not be performed if permission is not enabled")
                            .setNegativeButton(
                                "Cancel",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.cancelPermissionRequest()
                                })
                            .setPositiveButton(
                                "OK",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.continuePermissionRequest()
                                })
                            .show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (count1 > 0) {
                            var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            var uri: Uri =
                                Uri.fromParts("package", requireContext().packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        count1++
                    }
                }
                ).check()
        }

        binding.save.setOnClickListener {
            val name = binding.name.text.toString()
            val fromYear = binding.fromYear.text.toString()
            val toYear = binding.toYear.text.toString()
            val type = binding.typess.selectedItem.toString()
            val info = binding.info.text.toString()

            if (name.isEmpty() && fromYear.isEmpty() && toYear.isEmpty() && info.isEmpty()) {
                Toast.makeText(requireContext(), "Fill in all of the fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (image == null) {
                    Toast.makeText(
                        requireContext(),
                        "No image selected for user",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val id = System.currentTimeMillis().toString()
                    val users = Users(id = id, name = name, from = fromYear, to = toYear,type = type, info = info, photo = image)

                    firestore
                        .collection("users")
                        .document(id)
                        .set(users)
                        .addOnSuccessListener {
                            Log.d(TAG, "onCreateView: Success")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "onCreateView: Error")
                        }

                    findNavController().popBackStack()

                }
            }
        }

        return binding.root
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) {
            return@registerForActivityResult
        }
        binding.imageLoad.setImageURI(uri)

        val openInputStream = requireContext().contentResolver?.openInputStream(uri)
        val m = System.currentTimeMillis()
        val file = File(requireContext().filesDir, "$m.jpg")
        val fileOutputStream = FileOutputStream(file)
        openInputStream?.copyTo(fileOutputStream)
        openInputStream?.close()
        fileOutputStream.close()
        image = file.absolutePath.toString()
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
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}