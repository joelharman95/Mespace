/*
 * *
 *  * Created by Nethaji on 27/6/20 05:20 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 05:20 PM
 *
 */

package com.mespace.ui.view.appintro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount() = mFragmentList.size

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

}