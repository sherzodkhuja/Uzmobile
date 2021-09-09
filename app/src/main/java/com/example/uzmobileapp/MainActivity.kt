package com.example.uzmobileapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.uzmobileapp.databinding.ActivityMainBinding
import com.example.uzmobileapp.databinding.ContentMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityMainBinding
    lateinit var contentMain: ContentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.telegram -> {
                    val uri = Uri.parse("https://t.me/uzmobile" + this.packageName)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        drawerLayout.close()
//                        Snackbar.make(R.layout.activity_main, "Xatolik", Snackbar.LENGTH_LONG)
//                            .show()
                    }
                }
                R.id.contact -> {
                    drawerLayout.close()
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Biz bilan aloqa")
                    builder.setMessage("Aloqa uchun ushbu +99890 123 45 67 telefon raqamiga qo'ng'iroq qilishingiz mumkin")
                    builder.setPositiveButton("ORQAGA") { dialog, which ->

                    }
                    builder.show()
                }

                R.id.share -> {
                    drawerLayout.close()
                    try {
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Uzmobile ilovasini playmarketdan yuklab oling"
                        )
                        intent.type = "text/plain"
                        startActivity(Intent.createChooser(intent, "Share To:"))
                    } catch (e: Exception) {
                        drawerLayout.close()
//                        Snackbar.make(R.layout.activity_main, "Xatolik", Snackbar.LENGTH_LONG)
//                            .show()
                    }
                }

                R.id.rate -> {
                    try {
                        val uri =
                            Uri.parse("https://play.google.com/store/apps/details?id=uz.uztelecom.telecom&hl=ru&gl=US" + this.packageName)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    } catch (e: Exception) {
                        drawerLayout.close()
//                        Snackbar.make(R.layout.activity_main, "Xatolik", Snackbar.LENGTH_LONG)
//                            .show()
                    }
                }

                R.id.about -> {
                    drawerLayout.close()
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Biz haqimizda")
                    builder.setMessage("Ushbu ilova Uzmobile operatori mijozlariga qulaylik yaratish maqsadida ishlab chiqilgan bo'lib, sizning shaxsiy hisob varaqangizga va raqamingizga hech qanday zararli tomoni yo'q")
                    builder.setPositiveButton("ORQAGA") { dialog, which ->

                    }
                    builder.show()
                }
                else -> 1
            }
            true
        }

        binding.appBarMain.include.balanceBtn.setOnClickListener {
            try {
                //request for permission
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1
                    )
                } else {
                    //dial Ussd code
                    startActivity(
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:" + "*100" + Uri.encode("#"))
                        )
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Xatolik", Toast.LENGTH_SHORT).show()
            }
        }
        binding.appBarMain.include.operatorBtn.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.operatorFragment)
        }

        binding.appBarMain.include.newsBtn.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.newsFragment)
        }

        binding.appBarMain.include.settingsBtn.setOnClickListener {
            Toast.makeText(this, "Tilni o'zgartirish mavjud emas", Toast.LENGTH_SHORT).show()
        }

        binding.appBarMain.include.homeBtn.setOnClickListener {

            if (navController.currentDestination!!.id != R.id.nav_home) {
                navController.popBackStack()
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}