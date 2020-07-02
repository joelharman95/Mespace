/*
 * *
 *  * Created by Nethaji on 27/6/20 8:11 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 8:11 PM
 *
 */

package com.mespace.ui.view.storephone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.di.utility.BundleConstants.PHONE_NUMBER
import com.mespace.di.utility.applySpanPo
import kotlinx.android.synthetic.main.fragment_store_phone_no.*

/**
 * A simple [Fragment] subclass.
 */
class StorePhoneNoFragment : Fragment(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_phone_no, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvSignInOrUp.applySpanPo(
            getString(R.string.are_you_a_business),
            getString(R.string.label_sign_up),
            R.color.blue
        )

        ibBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnContinue.setOnClickListener {
            findNavController().navigate(
                R.id.action_storePhoneNoFragment_to_verifyPhoneNoFragment,
                bundleOf(PHONE_NUMBER to etPhoneNo.text.toString())
            )
        }
        tvSignInOrUp.setOnClickListener{
            findNavController().navigate(
                R.id.homeFragment
            )
        }
    }

}
