package com.example.uzmobileapp.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.FragmentAboutNewsBinding
import com.example.uzmobileapp.models.news.News
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "news"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutNewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news = it.getSerializable(ARG_PARAM1) as News?
        }
    }

    lateinit var binding: FragmentAboutNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutNewsBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Yangiliklar"

        if (news != null) {
            binding.newsTitle.text = news?.title
            binding.newsDescription.text = news?.description
            Picasso.get().load(news?.image).into(binding.newsImage)
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
         * @return A new instance of fragment AboutNewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}