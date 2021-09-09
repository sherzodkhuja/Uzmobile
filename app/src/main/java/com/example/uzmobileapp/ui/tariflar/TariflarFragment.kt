package com.example.uzmobileapp.ui.tariflar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.tariflar.TariflarAdapter
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.FragmentTariflarBinding
import com.example.uzmobileapp.models.Tariflar
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
 * Use the [TariflarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TariflarFragment : Fragment() {
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

    lateinit var binding: FragmentTariflarBinding
    lateinit var list: ArrayList<Tariflar>
    lateinit var tariflarAdapter: TariflarAdapter
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTariflarBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Tariflar"
        list = ArrayList()

        db.collection("Tariflar").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val ussd = documentChange.document.toObject(Tariflar::class.java)
                        list.add(ussd)
                        Log.d("Sher", "onCreateView: $ussd")
                    }
                }
            }

            tariflarAdapter = TariflarAdapter(list, object : TariflarAdapter.OnItemClickListener {
                override fun onItemClick(tariflar: Tariflar) {
                    var bundle = Bundle()
                    bundle.putSerializable("tarif", tariflar)
                    findNavController().navigate(R.id.aboutTarifFragment, bundle)
                }
            })
            binding.rv.adapter = tariflarAdapter
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
         * @return A new instance of fragment TariflarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TariflarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}