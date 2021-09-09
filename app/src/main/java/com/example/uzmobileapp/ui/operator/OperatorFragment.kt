package com.example.uzmobileapp.ui.operator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.operator.OperatorAdapter
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.FragmentOperatorBinding
import com.example.uzmobileapp.models.Operator
import com.example.uzmobileapp.models.Ussd
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OperatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OperatorFragment : Fragment() {
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

    lateinit var binding: FragmentOperatorBinding
    lateinit var operatorAdapter: OperatorAdapter
    lateinit var list: ArrayList<Operator>
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperatorBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Operator bilan aloqa"

        list = ArrayList()

        db.collection("Operator").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val operator = documentChange.document.toObject(Operator::class.java)
                        list.add(operator)
                    }
                }
            }

            operatorAdapter = OperatorAdapter(list, object : OperatorAdapter.OnItemClickListener {
                override fun onItemClick(operator: Operator) {

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("${operator.phone}")
                    builder.setMessage("${operator.description}")
                    builder.setNegativeButton("Orqaga") { dialog, which ->

                    }
                    builder.setPositiveButton("Qo'ng'iroq qilish") { dialog, which ->
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
                            } else {
                                //dial Ussd code
                                startActivity(
                                    Intent(
                                        Intent.ACTION_CALL,
                                        Uri.parse("tel:" + "${operator.phone}")
                                    )
                                )
                            }
                        } catch (e: Exception) {
//                        Toast.makeText(this, "Xatolik", Toast.LENGTH_SHORT).show()
                        }
                    }
                    builder.show()
                }

            })
            binding.rv.adapter = operatorAdapter
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
         * @return A new instance of fragment OperatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OperatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}