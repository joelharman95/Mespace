/*
 * *
 *  * Created by Nethaji on 27/6/20 3:00 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 11:01 AM
 *
 */

package com.mespace.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.mespace.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activityNavHost) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        if (findNavController(R.id.activityNavHost).currentDestination?.id != null) {
            when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                R.id.splashFragment, R.id.appIntroFragment, R.id.storePhoneNoFragment, R.id.homeFragment -> {
                    finish()
                }
                else -> findNavController(R.id.activityNavHost).navigateUp()
            }
        } else super.onBackPressed()
    }

}
