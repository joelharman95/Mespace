package com.mespace.ui.view.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.LifecycleObserver

import com.mespace.R
import com.mespace.ui.view.appintro.PagerAdapter
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoriesFragment : Fragment(), LifecycleObserver {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(ActiveFragment(), "Active")
        pagerAdapter.addFragment(BusinessFragment(), "Business")
        pagerAdapter.addFragment(CommunityFragment(), "My Community")
        pager.adapter = pagerAdapter
        tab_lay.setupWithViewPager(pager)
    }


}
