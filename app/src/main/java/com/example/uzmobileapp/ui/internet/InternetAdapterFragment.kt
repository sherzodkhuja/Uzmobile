package com.example.uzmobileapp.ui.internet

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
import com.example.uzmobileapp.adapters.internet.InternetAdapter
import com.example.uzmobileapp.databinding.DialogBottomBinding
import com.example.uzmobileapp.databinding.FragmentInternetAdapterBinding
import com.example.uzmobileapp.models.daqiqa.Daqiqa
import com.example.uzmobileapp.models.internet.Internet
import com.example.uzmobileapp.models.internet.InternetCategory
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InternetAdapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InternetAdapterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: InternetCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as InternetCategory
        }
    }

    lateinit var binding: FragmentInternetAdapterBinding
    lateinit var adapter: InternetAdapter
    lateinit var firestore: FirebaseFirestore
    lateinit var list: ArrayList<Internet>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInternetAdapterBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()
        list = ArrayList()

        firestore.collection("Internet").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val daqiqa = documentChange.document.toObject(Internet::class.java)
                        if (daqiqa.expire_date.equals(param1?.name)) {
                            list.add(daqiqa)
                        }
                    }
                }
                adapter = InternetAdapter(list, object : InternetAdapter.OnItemClickListener {
                    override fun onItemClick(internet: Internet) {

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

                        dialogBottomBinding.name.text = internet.name
                        dialogBottomBinding.price.text = "To'plam narxi: " + internet.price
                        dialogBottomBinding.amount.text =
                            "Berilgan trafik: " + internet.amount + "MB"
                        dialogBottomBinding.expireDate.text =
                            "Amal qilish muddati: " + internet.expire_date
                        dialogBottomBinding.code.text = "Faollashtirish: " + internet.code

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
                                } else {//read code from firebase
                                    var code = internet.code?.length?.minus(1)?.let {
                                        internet.code?.substring(
                                            0,
                                            it
                                        )
                                    }
                                    //dial Ussd code
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_CALL,
                                            Uri.parse("tel:" + "${internet.code}" + Uri.encode("#"))
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
                                    "\nUzmobiledan ${internet.name} tarifi: \nNarxi: \"+${internet.price}\nMB lar soni: \"+${internet.amount + "MB"}\nFaollashtirish: \"+${internet.code}\""
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
         * @return A new instance of fragment InternetAdapterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(category: InternetCategory) =
            InternetAdapterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}