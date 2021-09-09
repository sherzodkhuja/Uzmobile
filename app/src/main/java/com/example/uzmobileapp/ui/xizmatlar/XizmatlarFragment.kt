package com.example.uzmobileapp.ui.xizmatlar

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.adapters.xizmatlar.XizmatlarAdapter
import com.example.uzmobileapp.databinding.DialogTopBinding
import com.example.uzmobileapp.databinding.FragmentXizmatlarBinding
import com.example.uzmobileapp.models.Xizmatlar
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [XizmatlarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class XizmatlarFragment : Fragment() {
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

    lateinit var binding: FragmentXizmatlarBinding
    lateinit var xizmatlarAdapter: XizmatlarAdapter
    lateinit var list: ArrayList<Xizmatlar>
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentXizmatlarBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Xizmatlar"
        list = ArrayList()

        db.collection("Xizmatlar").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val xizmatlar = documentChange.document.toObject(Xizmatlar::class.java)
                        list.add(xizmatlar)
                    }
                }
            }
            xizmatlarAdapter =
                XizmatlarAdapter(list, object : XizmatlarAdapter.OnItemClickListener {
                    override fun onItemClickCall(xizmatlar: Xizmatlar) {
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
                                var code = xizmatlar.code?.length?.minus(1)?.let {
                                    xizmatlar.code?.substring(
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

                    override fun onItemClickBatafsil(xizmatlar: Xizmatlar) {

                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(true)

                        val dialogBottomBinding: DialogTopBinding =
                            DialogTopBinding.inflate(inflater)
                        dialog.setContentView(dialogBottomBinding.root)

                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(dialog.getWindow()?.getAttributes())
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

                        dialogBottomBinding.description.text = xizmatlar?.description
                        dialogBottomBinding.name.text = xizmatlar?.name

                        dialogBottomBinding.backBtn.setOnClickListener {
                            dialog.cancel()
                        }

                        dialogBottomBinding.shareBtn.setOnClickListener {
                            try {
                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.type = "text/plain"
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                                var shareMessage =
                                    "\nUzmobiledan ${xizmatlar?.name}:\nFaollashtirish: \"+${xizmatlar?.code}\""
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
            binding.rv.adapter = xizmatlarAdapter
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
         * @return A new instance of fragment XizmatlarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            XizmatlarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}