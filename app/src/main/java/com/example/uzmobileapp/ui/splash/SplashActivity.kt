package com.example.uzmobileapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.uzmobileapp.MainActivity
import com.example.uzmobileapp.R
import com.example.uzmobileapp.databinding.ActivitySplashBinding
import com.example.uzmobileapp.databinding.FragmentLanguageBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.uzmobile.alpha = 0f
//        binding.uzmobile.animate().setDuration(1500).alpha(1f).withEndAction {
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//            finish()
//        }
    }
}