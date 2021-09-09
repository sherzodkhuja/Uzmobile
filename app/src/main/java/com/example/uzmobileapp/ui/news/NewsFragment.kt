package com.example.uzmobileapp.ui.news

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.adapters.news.NewsAdapter
import com.example.uzmobileapp.adapters.ussd.UssdAdapter
import com.example.uzmobileapp.databinding.FragmentNewsBinding
import com.example.uzmobileapp.models.Ussd
import com.example.uzmobileapp.models.news.News
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
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

    lateinit var binding: FragmentNewsBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var list: ArrayList<News>
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Yangiliklar"

        list = ArrayList()

        db.collection("Yangiliklar").addSnapshotListener { value, error ->
            value?.documentChanges?.forEach { documentChange ->
                when (documentChange.type) {
                    DocumentChange.Type.ADDED -> {
                        val news = documentChange.document.toObject(News::class.java)
                        list.add(news)
                    }
                }
            }

            newsAdapter = NewsAdapter(list, object : NewsAdapter.OnItemClickListener {
                override fun onItemClick(news: News) {
                    var bundle = Bundle()
                    bundle.putSerializable("news", news)
                    findNavController().navigate(R.id.aboutNewsFragment, bundle)
                }
            })
            binding.rv.adapter = newsAdapter
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
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}