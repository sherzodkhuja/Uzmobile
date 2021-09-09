package com.example.uzmobileapp.ui.ussd

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.FragmentUssdBinding
import com.example.uzmobileapp.models.Ussd
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UssdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UssdFragment : Fragment() {
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

    lateinit var binding: FragmentUssdBinding
    lateinit var ussdAdapter: UssdAdapter
    lateinit var list: ArrayList<Ussd>
    var db = Firebase.firestore

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUssdBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "USSD"

        list = ArrayList()

        db.collection("USSD").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val ussd = documentChange.document.toObject(Ussd::class.java)
                        list.add(ussd)
                    }
                }
            }
            ussdAdapter = UssdAdapter(list, object : UssdAdapter.OnItemClickListener {
                override fun onItemClick(ussd: Ussd) {
                    try {
                        //request for permission
                        if (ActivityCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                1
                            )
                        } else {//read code from firebase
                            var code = ussd.code?.length?.minus(1)?.let {
                                ussd.code?.substring(
                                    0,
                                    it
                                )
                            }
                            //dial Ussd code
                            startActivity(
                                Intent(
                                    Intent.ACTION_CALL,
                                    Uri.parse("tel:" + "$code" + Uri.encode("#"))
                                )
                            )
                        }
                    } catch (e: Exception) {
//                        Toast.makeText(this, "Xatolik", Toast.LENGTH_SHORT).show()
                    }
                }

            })
            binding.rv.adapter = ussdAdapter
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
         * @return A new instance of fragment UssdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UssdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}