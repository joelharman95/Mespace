package com.mespace.ui.view.verifyphonenumber

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.mespace.R
import com.mespace.di.blockInput
import com.mespace.di.dismissKeyboard
import com.mespace.di.utility.BundleConstants.PHONE_NUMBER
import com.mespace.di.utility.applySpanPo
import com.mespace.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_verify_phone_no.*
import java.util.regex.Pattern

class VerifyPhoneNoFragment : Fragment(), LifecycleObserver {

    private var otpBroadcastReceiver: OTPBroadcastReceiver? = null
    private val REQ_USER_CONSENT = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(otpBroadcastReceiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_verify_phone_no, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPhoneSubtitle.applySpanPo(
            getString(R.string.label_check_sms_msg),
            arguments?.getString(PHONE_NUMBER).toString(),
            R.color.grey
        )
        tvResendOtp.applySpanPo(
            getString(R.string.label_didn_t_receive_sms),
            getString(R.string.label_resend_code),
            R.color.blue
        )

        ibBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnVerify.setOnClickListener {
            blockInput(pbVerifyOtp)
            (activity as MainActivity).verifyPhoneNumberWithCode(
                code = OptEts.text.toString(), _progressBar = pbVerifyOtp
            )
            //  findNavController().navigate(R.id.action_verifyPhoneNoFragment_to_profileSetupFragment)
        }

        OptEts.setOtpCompletionListener {
            dismissKeyboard(view)
        }

        tvResendOtp.setOnClickListener {
            blockInput(pbVerifyOtp)
            (activity as MainActivity).resendVerificationCode(
                arguments?.getString(PHONE_NUMBER).toString(),
                _progressBar = pbVerifyOtp
            )
        }

        startSmsUserConsent()

    }

    private fun startSmsUserConsent() {
        val client = SmsRetriever.getClient(this.requireActivity())
        client.startSmsUserConsent(null)
            .addOnSuccessListener {
                //  activity?.toast("On Success")
            }.addOnFailureListener {
                //   activity?.toast("On OnFailure")
            }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val smsString = message?.replace("[^0-9]", "").toString()
                Log.e("smsString", smsString)
                Log.e("smsString", message)
                getOtpFromMessage(smsString)
            }
        }
    }

    private fun getOtpFromMessage(message: String) {
        val pattern = Pattern.compile("(|^)\\d{6}")
        val matcher = pattern.matcher(message)
        if (matcher.find()) {
            OptEts?.setText(matcher.group(0))
        }
    }

    private fun registerBroadcastReceiver() {
        otpBroadcastReceiver = OTPBroadcastReceiver()
        otpBroadcastReceiver?.smsBroadcastReceiverListener =
            object : OTPBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent) {
                    startActivityForResult(
                        intent,
                        REQ_USER_CONSENT
                    )
                }

                override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        context?.registerReceiver(otpBroadcastReceiver, intentFilter)
    }

}
