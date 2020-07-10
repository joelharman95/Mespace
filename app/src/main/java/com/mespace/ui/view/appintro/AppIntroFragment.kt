/*
 * *
 *  * Created by Nethaji on 27/6/20 5:05 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 5:05 PM
 *
 */

package com.mespace.ui.view.appintro

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.data.preference.PreferenceManager
import kotlinx.android.synthetic.main.app_intro_fragment.*


class AppIntroFragment : Fragment(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.app_intro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.WHITE)
        }

        PreferenceManager(requireContext()).apply {
            saveIsLaunchedOnce(true)
        }

        tvSkip.setOnClickListener {
            findNavController().navigate(R.id.action_appIntroFragment_to_storePhoneNoFragment)
        }

        val pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(
            ScreenFragment.newInstance(
                1,
                getString(R.string.label_socialize_at_a_distance),
                getString(R.string.label_socialize_subtitle),
                R.drawable.ic_walkthrough_1
            )
        )
        pagerAdapter.addFragment(
            ScreenFragment.newInstance(
                2,
                getString(R.string.label_broadcast_your_ideas),
                getString(R.string.label_location_announcement),
                R.drawable.ic_walkthrough_2
            )
        )
        pagerAdapter.addFragment(
            ScreenFragment.newInstance(
                3,
                getString(R.string.label_virtual_customer_interaction),
                getString(R.string.label_customer_interaction_subtitle),
                R.drawable.ic_walkthrough_3
            )
        )

        vpAppIntro.adapter = pagerAdapter

    }

}
