/*
 * *
 *  * Created by Nethaji on 27/6/20 3:00 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 11:01 AM
 *
 */

package com.mespace.ui

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.mespace.R
import com.mespace.di.toast
import com.mespace.di.unblockInput
import com.mespace.di.utility.BundleConstants.PHONE_NUMBER
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private var verificationInProgress = false
    private var phoneNumber = ""
    private var progressBar: ProgressBar? = null
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()  // Initialize Firebase Auth

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                println("GET______onVerificationCompleted:_______$credential")
                verificationInProgress = false
                //  signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                println("GET______onVerificationFailed:__________$e")
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                        R.id.verifyPhoneNoFragment -> {
                            findNavController(R.id.activityNavHost).navigateUp()
                        }
                    }
                    toast(e.toString())
                } else if (e is FirebaseTooManyRequestsException) {
                    toast("Quota exceeded.")
                } else
                    toast("Failed to send otp")
                updateUI(STATE_VERIFY_FAILED)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                updateUI(STATE_CODE_SENT)
            }
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activityNavHost) as NavHostFragment
        val navController = navHostFragment.navController
    }

    fun startPhoneNumberVerification(
        _phoneNumber: String,
        _progressBar: ProgressBar
    ) {
        phoneNumber = _phoneNumber
        progressBar = _progressBar
        verificationInProgress = true
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(_phoneNumber, 60, TimeUnit.SECONDS, this, callbacks)
    }

    fun verifyPhoneNumberWithCode(
        verificationId: String? = null,
        code: String,
        _progressBar: ProgressBar
    ) {
        progressBar = _progressBar
        if (storedVerificationId == null) {
            toast(getString(R.string.label_no_verification_id))
            if (progressBar != null)
                unblockInput(progressBar!!)
            return
        }
        val credential = PhoneAuthProvider.getCredential(storedVerificationId.toString(), code)
        signInWithPhoneAuthCredential(credential)
    }

    fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken? = null,
        _progressBar: ProgressBar
    ) {
        progressBar = _progressBar
        if (resendToken != null) {
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callbacks, resendToken)
        } else {
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callbacks)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task ->
            if (progressBar != null)
                unblockInput(progressBar!!)
            if (task.isSuccessful) {
                println("GET_________signInWithCredential:success")
                val user = task.result?.user
                updateUI(STATE_SIGNIN_SUCCESS, user)
            } else {
                println("GET________signInWithCredential:failure_____" + task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    /*when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                        R.id.verifyPhoneNoFragment -> {
                            findNavController(R.id.activityNavHost).navigateUp()
                        }
                    }*/
                }
                updateUI(STATE_SIGNIN_FAILED)
            }
        }
    }

    private fun updateUI(
        uiState: Int,
        user: FirebaseUser? = auth?.currentUser,
        cred: PhoneAuthCredential? = null
    ) {
        if (progressBar != null)
            unblockInput(progressBar!!)
        when (uiState) {
            STATE_INITIALIZED -> {
            }
            STATE_CODE_SENT -> {
                println("GET________STATE_CODE_SENT")
                when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                    R.id.storePhoneNoFragment -> {
                        findNavController(R.id.activityNavHost).navigate(
                            R.id.verifyPhoneNoFragment,
                            bundleOf(PHONE_NUMBER to phoneNumber)
                        )
                    }
                }
            }
            STATE_VERIFY_FAILED -> {
                println("GET________STATE_VERIFY_FAILED")
                /*when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                    R.id.verifyPhoneNoFragment -> {
                        findNavController(R.id.activityNavHost).navigateUp()
                    }
                }*/
            }
            STATE_VERIFY_SUCCESS -> {
                if (cred != null) {
                    if (cred.smsCode != null) {
                        when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                            R.id.storePhoneNoFragment -> {
                                findNavController(R.id.activityNavHost).navigate(R.id.profileSetupFragment)
                            }
                        }
                        println("GET________" + cred.smsCode)
                    } else {
                        when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                            R.id.storePhoneNoFragment -> {
                                findNavController(R.id.activityNavHost).navigate(R.id.profileSetupFragment)
                            }
                        }
                        println("GET________instant_validation")
                    }
                }
            }
            STATE_SIGNIN_FAILED -> {
                /*when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                    R.id.verifyPhoneNoFragment -> {
                        findNavController(R.id.activityNavHost).navigateUp()
                    }
                }*/
            }
            STATE_SIGNIN_SUCCESS -> {
                when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                    R.id.verifyPhoneNoFragment -> {
                        findNavController(R.id.activityNavHost).navigate(
                            R.id.profileSetupFragment,
                            bundleOf(PHONE_NUMBER to phoneNumber)
                        )
                    }
                }
            }
        }

        /*if (user == null) {
            when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                R.id.verifyPhoneNoFragment -> {
                    findNavController(R.id.activityNavHost).navigateUp()
                }
            }
        } else {
            when (findNavController(R.id.activityNavHost).currentDestination?.id) {
                R.id.storePhoneNoFragment, R.id.verifyPhoneNoFragment -> {
                    findNavController(R.id.activityNavHost).navigate(
                        R.id.profileSetupFragment,
                        bundleOf(PHONE_NUMBER to phoneNumber)
                    )
                }
            }
        }*/
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

    companion object {
        private const val TAG = "MainActivity"
        private const val STATE_INITIALIZED = 1
        private const val STATE_VERIFY_FAILED = 3
        private const val STATE_VERIFY_SUCCESS = 4
        private const val STATE_CODE_SENT = 2
        private const val STATE_SIGNIN_FAILED = 5
        private const val STATE_SIGNIN_SUCCESS = 6
    }
}
