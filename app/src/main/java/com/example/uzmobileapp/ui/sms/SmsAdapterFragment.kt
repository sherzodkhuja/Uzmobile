package com.example.uzmobileapp.ui.sms

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.internet.InternetAdapter
import com.example.uzmobileapp.adapters.sms.SmsAdapter
import com.example.uzmobileapp.databinding.DialogBottomBinding
import com.example.uzmobileapp.databinding.FragmentSmsAdapterBinding
import com.example.uzmobileapp.models.internet.Internet
import com.example.uzmobileapp.models.sms.Sms
import com.example.uzmobileapp.models.sms.SmsCategory
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SmsAdapterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SmsAdapterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: SmsCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as SmsCategory?
        }
    }

    lateinit var binding: FragmentSmsAdapterBinding
    lateinit var adapter: SmsAdapter
    lateinit var firestore: FirebaseFirestore
    lateinit var list: ArrayList<Sms>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsAdapterBinding.inflate(inflater, container, false)

        firestore = FirebaseFirestore.getInstance()
        list = ArrayList()

        firestore.collection("SMS").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val sms = documentChange.document.toObject(Sms::class.java)
                        if (sms.expire_date.equals(param1?.name)) {
                            list.add(sms)
                        }
                    }
                }
                adapter = SmsAdapter(list, object : SmsAdapter.OnItemClickListener {
                    override fun onItemClick(sms: Sms) {

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

                        dialogBottomBinding.name.text = sms.name
                        dialogBottomBinding.price.text = "Narxi: " + sms.price
                        dialogBottomBinding.amount.text =
                            "Berilgan smslar soni: " + sms.amount
                        dialogBottomBinding.expireDate.text =
                            "Amal qilish muddati: " + sms.expire_date
                        dialogBottomBinding.code.text = "Faollashtirish: " + sms.code

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
                                    var code = sms.code?.length?.minus(1)?.let {
                                        sms.code?.substring(
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

                        dialogBottomBinding.shareBtn.setOnClickListener {
                            try {
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                                var shareMessage =
                                    "\nUzmobiledan ${sms.name}: \nNarxi: \"+${sms.price}\nSMS lar soni: \"+${sms.amount}\nFaollashtirish: \"+${sms.code}\""
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
         * @return A new instance of fragment SmsAdapterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(category: SmsCategory) =
            SmsAdapterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, category)
                }
            }
    }
}