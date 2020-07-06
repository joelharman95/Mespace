package com.mespace.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.mespace.R
import com.mespace.data.viewmodel.HomeViewModel
import com.mespace.data.viewmodel.ProfileViewModel
import com.mespace.di.blockInput
import com.mespace.di.loadCircularImage
import com.mespace.di.toast
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile_setup.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), LifecycleObserver {
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_image.loadCircularImage("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
getUserdetails()
    }

    private fun getUserdetails() {
        blockInput(pbHome)
        homeViewModel.getUserList(
            {
                unblockInput(pbHome)
                println(" Data "+ it.detail.mespace_list)

            },{
                unblockInput(pbHome)
                activity?.toast(it)

            }
        )

    }

}
