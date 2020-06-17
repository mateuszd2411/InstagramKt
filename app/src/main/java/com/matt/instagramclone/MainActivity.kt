package com.matt.instagramclone

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                textMessage.setText("Home")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search -> {
                textMessage.setText("Search")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add_post -> {
                textMessage.setText("Add Post")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                textMessage.setText("Notifications")
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                textMessage.setText("Profile")
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)

        navView.setOnNavigationItemSelectedListener (onNavigationItemSelectedListener)


    }
}
