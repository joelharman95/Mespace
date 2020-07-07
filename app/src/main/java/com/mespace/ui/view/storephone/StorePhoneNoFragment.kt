/*
 * *
 *  * Created by Nethaji on 27/6/20 8:11 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 8:11 PM
 *
 */

package com.mespace.ui.view.storephone

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.di.blockInput
import com.mespace.di.toast
import com.mespace.di.utility.BundleConstants.IS_BUSINESS
import com.mespace.di.utility.applySpanPo
import com.mespace.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_store_phone_no.*

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

        arguments?.getBoolean(IS_BUSINESS)?.apply {
            if (this) {
                tvSignInOrUp.applySpanPo(
                    getString(R.string.are_you_a_user),
                    getString(R.string.label_login),
                    R.color.blue
                )
                tvPhoneNo.text = getString(R.string.label_your_business_phone_no)
                ibBack.visibility = View.VISIBLE
            } else {
                tvSignInOrUp.applySpanPo(
                    getString(R.string.are_you_a_business),
                    getString(R.string.label_sign_up),
                    R.color.blue
                )
                tvPhoneNo.text = getString(R.string.label_your_phone_no)
                ibBack.visibility = View.INVISIBLE
            }
        }

        ibBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_storePhoneNoFragment_self,
                bundleOf(IS_BUSINESS to false)
            )
        }

        btnContinue.setOnClickListener {
            if (TextUtils.isEmpty(tiePhoneNo.text.toString())) {
                tiePhoneNo.error = getString(R.string.label_invalid_no)
                activity?.toast(getString(R.string.label_invalid_no))
                return@setOnClickListener
            }
            blockInput(pbPhone)
            (activity as MainActivity).startPhoneNumberVerification(
                ccpEt.selectedCountryCodeWithPlus.toString(), tiePhoneNo.text.toString(),
                pbPhone
            )
            /*findNavController().navigate(
                R.id.action_storePhoneNoFragment_to_verifyPhoneNoFragment,
                bundleOf(PHONE_NUMBER to tiePhoneNo.text.toString())
            )*/
        }
        tvSignInOrUp.setOnClickListener {

            ccpEt.performClick()

        /*    if (arguments?.getBoolean(IS_BUSINESS) != null && arguments?.getBoolean(IS_BUSINESS)!!) {
                findNavController().navigate(
                    R.id.action_storePhoneNoFragment_self,
                    bundleOf(IS_BUSINESS to false)
                )
            } else {
                findNavController().navigate(
                    R.id.action_storePhoneNoFragment_self,
                    bundleOf(IS_BUSINESS to true)
                )
            }*/
        }
    }

}
