/*
 * *
 *  * Created by Nethaji on 27/6/20 5:08 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 5:05 PM
 *
 */

package com.mespace.ui.view.splash

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.data.network.api.request.ReferenceRequest
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.ReferenceViewModel
import com.mespace.di.blockInput
import com.mespace.di.isConnected
import com.mespace.di.toast
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(), LifecycleObserver {

    private val viewModel by viewModel<ReferenceViewModel>()
    private val MY_PERMISSIONS_REQUEST_GPS = 111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            delay(2000)
          /*PreferenceManager(requireContext()).apply {
                if (getIsLaunchedOnce()) {
                    if (getUserId() != "")
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    else
                        findNavController().navigate(R.id.action_splashFragment_to_storePhoneNoFragment)
                } else
                    findNavController().navigate(R.id.action_splashFragment_to_appIntroFragment)
            }*/

            findNavController().navigate(R.id.action_splashFragment_to_storePhoneNoFragment)
        }
        //  getToken()
    }

    //  Reference api call
    private fun getToken() {
        if (isConnected(this.requireActivity())) {
            blockInput(pbMainActivity)
            viewModel.getToken(ReferenceRequest(requestString = "requestString"), onSuccess = {
                unblockInput(pbMainActivity)
                //  Function here
            }, onError = {
                unblockInput(pbMainActivity)
                activity?.toast("")

            })
        } else
            activity?.toast("No internet available!")
    }



}
