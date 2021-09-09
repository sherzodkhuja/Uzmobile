package com.example.uzmobileapp.ui.tariflar

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.DialogBottomBinding
import com.example.uzmobileapp.databinding.DialogTopBinding
import com.example.uzmobileapp.databinding.FragmentAboutTarifBinding
import com.example.uzmobileapp.models.Tariflar
import com.google.firebase.firestore.BuildConfig

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "tarif"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutTarifFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutTarifFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var tariflar: Tariflar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tariflar = it.getSerializable(ARG_PARAM1) as Tariflar
        }
    }

    lateinit var binding: FragmentAboutTarifBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutTarifBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = "Tarif haqida"

        binding.callBtn.setOnClickListener {
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
                    //dial Ussd code
                    startActivity(
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:" + "${tariflar?.code}" + Uri.encode("#"))
                        )
                    )
                }
            } catch (e: Exception) {
            }
        }

        binding.batafsilBtn.setOnClickListener {

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

            dialogBottomBinding.description.text = tariflar?.description
            dialogBottomBinding.name.text = tariflar?.name

            dialogBottomBinding.backBtn.setOnClickListener {
                dialog.cancel()
            }

            dialogBottomBinding.shareBtn.setOnClickListener {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    var shareMessage =
                        "\nUzmobiledan ${tariflar?.name}:\nFaollashtirish: \"+${tariflar?.code}\""
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

        binding.tarifMb.text = tariflar?.mb
        binding.tarifMinute.text = tariflar?.minute_inside
        binding.tarifSms.text = tariflar?.sms
        binding.tarifName.text = tariflar?.name
        binding.tarifName2.text = tariflar?.name
        binding.tarifPrice.text = "Oyiga " + tariflar?.monthly_cost
        binding.tarifPrice2.text = tariflar?.monthly_cost

        binding.minuteUz.text = "O'zbekiston bo'yicha: " + tariflar?.minute_uzb
        binding.minuteInside.text = "Taqmoq ichida: " + tariflar?.minute_inside
        binding.mb.text = "Mobil internet: " + tariflar?.mb
        binding.tarmoqSms.text = "O'zbekiston bo'yicha sms: " + tariflar?.sms

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutTarifFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(tariflar: Tariflar) =
            AboutTarifFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, tariflar)
                }
            }
    }
}