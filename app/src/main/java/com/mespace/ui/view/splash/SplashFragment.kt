/*
 * *
 *  * Created by Nethaji on 27/6/20 5:08 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 5:05 PM
 *
 */

package com.mespace.ui.view.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.data.network.api.request.ReferenceRequest
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.ReferenceViewModel
import com.mespace.di.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment(), LifecycleObserver {
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
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
        if (Build.VERSION.SDK_INT >= 21) {
            val window: Window = requireActivity().getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.splash_iamge)
        }

        if (!checkRuntimePermission()) {
            requestRuntimePermission()
        }
        else {
            moveNextScreen()
        }


}


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


    private fun checkRuntimePermission(): Boolean {

        val writeablePermission =
                ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE

                )
        if (writeablePermission != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }



    private fun requestRuntimePermission() {
        requestPermissions(
                arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                ), READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                val granted = grantResults.isNotEmpty()
                        && permissions.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && !ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permissions[0]
                )
                when (granted) {
                    true ->moveNextScreen()  //  openImagePicker()
                    else -> activity?.toast(resources.getString(R.string.permission_denied))
                }
            }
        }
    }

    fun moveNextScreen()
    {
        lifecycleScope.launch {
            delay(2000)
       PreferenceManager(requireContext()).apply {
               if (getIsLaunchedOnce()) {
                    if (getUserId() != "")
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    else
                        findNavController().navigate(R.id.action_splashFragment_to_storePhoneNoFragment)
                } else
                    findNavController().navigate(R.id.action_splashFragment_to_appIntroFragment)

        //   findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }


            //  getToken()
        }
    }


}
