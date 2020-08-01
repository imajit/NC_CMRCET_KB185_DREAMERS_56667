package com.example.sih2020.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.sih2020.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupWithNavController(navigation_view,navController)

        NavigationUI.setupActionBarWithNavController(this,navController,drawer_layout)
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            drawer_layout
        )
    }


}