package com.example.uzmobileapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uzmobileapp.BuildConfig
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.PagerAdapter2
import com.example.uzmobileapp.adapters.ViewPagerAdapter
import com.example.uzmobileapp.adapters.xizmatlar.XizmatlarAdapter
import com.example.uzmobileapp.databinding.FragmentHomeBinding
import com.example.uzmobileapp.models.Tariflar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewPagerAdapter: PagerAdapter2
    lateinit var list: ArrayList<Tariflar>

    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Home"
        list = ArrayList()

        db.collection("Tariflar").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val ussd = documentChange.document.toObject(Tariflar::class.java)
                        if (list.size < 4) {
                            list.add(ussd)
                        }
                    }
                }
            }

            viewPagerAdapter = PagerAdapter2(list, object : PagerAdapter2.OnItemClickListener {
                override fun onItemClick(tariflar: Tariflar) {
                    var bundle = Bundle()
                    bundle.putSerializable("tarif", tariflar)
                    findNavController().navigate(R.id.aboutTarifFragment, bundle)
                }
            })
            binding.viewPager2.adapter = viewPagerAdapter
            binding.indicator.setViewPager2(binding.viewPager2)
        }

        binding.ssd.setOnClickListener {
            findNavController().navigate(R.id.ussdFragment)
        }

        binding.img.setOnClickListener {
            findNavController().navigate(R.id.ussdFragment)
        }

        binding.txt.setOnClickListener {
            findNavController().navigate(R.id.ussdFragment)
        }

        binding.tariflar.setOnClickListener {
            findNavController().navigate(R.id.tariflarFragment)
        }

        binding.xizmatlar.setOnClickListener {
            findNavController().navigate(R.id.xizmatlarFragment)
        }
        binding.internet.setOnClickListener {
            findNavController().navigate(R.id.internetFragment)
        }

        binding.daqiqalar.setOnClickListener {
            findNavController().navigate(R.id.daqiqaFragment)
        }

        binding.smslar.setOnClickListener {
            findNavController().navigate(R.id.smsFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_telegram -> {
                val uri = Uri.parse("https://t.me/uzmobile" + requireActivity().packageName)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                }
            }
            R.id.action_share -> {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                    var shareMessage = "\nLet me recommend you this application\n\n"
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
        }
        return super.onOptionsItemSelected(item)
    }

}