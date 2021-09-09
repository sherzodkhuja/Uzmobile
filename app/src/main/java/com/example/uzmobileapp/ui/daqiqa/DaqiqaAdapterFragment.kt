package com.example.uzmobileapp.ui.daqiqa

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.uzmobileapp.adapters.daqiqa.DaqiqaAdapter
import com.example.uzmobileapp.databinding.DialogBottomBinding
import com.example.uzmobileapp.databinding.FragmentDaqiqaAdapterBinding
import com.example.uzmobileapp.models.daqiqa.Daqiqa
import com.example.uzmobileapp.models.daqiqa.DaqiqaCategory
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DaqiqaAdapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DaqiqaAdapterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: DaqiqaCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as DaqiqaCategory?
        }
    }

    lateinit var binding: FragmentDaqiqaAdapterBinding
    lateinit var adapter: DaqiqaAdapter
    lateinit var firestore: FirebaseFirestore
    lateinit var list: ArrayList<Daqiqa>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaqiqaAdapterBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()
        list = ArrayList()

        firestore.collection("Daqiqa").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val daqiqa = documentChange.document.toObject(Daqiqa::class.java)
                        if (daqiqa.type.equals(param1?.name)) {
                            list.add(daqiqa)
                        }
                    }
                }
                adapter = DaqiqaAdapter(list, object : DaqiqaAdapter.OnItemClickListener {
                    override fun onItemClick(daqiqa: Daqiqa) {

                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(true)

                        val dialogBottomBinding: DialogBottomBinding =
                            DialogBottomBinding.inflate(inflater)
                        dialog.setContentView(dialogBottomBinding.root)

                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(dialog.getWindow()?.getAttributes())
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

                        dialogBottomBinding.name.text = daqiqa.name
                        dialogBottomBinding.price.text = "Narxi: " + daqiqa.price
                        dialogBottomBinding.amount.text = "Berilgan daqiqalar soni: " + daqiqa.minut
                        dialogBottomBinding.expireDate.text =
                            "Amal qilish muddati: " + daqiqa.expire_date
                        dialogBottomBinding.code.text = "Faollashtirish: " + daqiqa.code + "#"

                        dialogBottomBinding.backBtn.setOnClickListener {
                            dialog.cancel()
                        }
                        dialogBottomBinding.activateBtn.setOnClickListener {
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
                                            Uri.parse("tel:" + "${daqiqa.code}" + Uri.encode("#"))
                                        )
                                    )
                                }
                            } catch (e: Exception) {
//                        Toast.makeText(this, "Xatolik", Toast.LENGTH_SHORT).show()
                            }
                        }

                        dialogBottomBinding.shareBtn.setOnClickListener {
                            try {
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                                var shareMessage =
                                    "\nUzmobiledan ${daqiqa.name} tarifi: \nNarxi: \"+${daqiqa.price}\ndaqiqalar soni: \"+${daqiqa.minut}\nFaollashtirish: \"+${daqiqa.code}+\"#\""
                                shareMessage =
                                    """
                            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                            
                            
                            """.trimIndent()
                                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                                startActivity(Intent.createChooser(shareIntent, "choose one"))
                            } catch (e: java.lang.Exception) {
                                //e.toString();
                            }
                        }
                        dialog.show()
                        dialog.getWindow()?.setAttributes(lp)
                    }
                })
                binding.rv.adapter = adapter
            }

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
         * @return A new instance of fragment DaqiqaAdapterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(category: DaqiqaCategory) =
            DaqiqaAdapterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}