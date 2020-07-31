package com.mespace.ui.view.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mespace.R
import com.mespace.data.network.api.request.ProfileSettingRequest
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.ProfileSettingViewModel
import com.mespace.di.blockInput
import com.mespace.di.loadCircularImage
import com.mespace.di.toast
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment(), LifecycleObserver {

    private var bottomSheetFragment: MySpaceBottomFragment? = null
    private val profileSettingViewModel by viewModel<ProfileSettingViewModel>()
    private var help_page: String = ""
    private var about: String = ""
    private var terms_condition: String = ""
    private var privacy_policy: String = ""
    private var invite_message: String = ""
    private var invite_url: String = ""

    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivEdit.setOnClickListener {
            Toast.makeText(context,"Working On It",Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.editProfileFragment)
        }

        tvProfileMySpace.setOnClickListener {
//            bottomSheet()
            Toast.makeText(context,"Working On It",Toast.LENGTH_SHORT).show()
        }

        ivCategoryBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        ivHome.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        tvTermsAndCon.setOnClickListener {
            PreferenceManager(requireContext()).apply {
                setUrl(terms_condition)
                setTitle("Terms And Conditions")
            }
            findNavController().navigate(R.id.webFragment)

        }

        tvPrivacyPolicy.setOnClickListener {
            PreferenceManager(requireContext()).apply {
                setUrl(privacy_policy)
                setTitle("Privacy Policy")
            }
            findNavController().navigate(R.id.webFragment)

        }

        tvProfileHelp.setOnClickListener {
            PreferenceManager(requireContext()).apply {
                setUrl(help_page)
                setTitle("Help")
            }
            findNavController().navigate(R.id.webFragment)
        }
        tvProfileAbout.setOnClickListener {
            PreferenceManager(requireContext()).apply {
                setUrl(about)
                setTitle("About")
            }
            findNavController().navigate(R.id.webFragment)

        }

        getProfileSettings()
    }

    @SuppressLint("SetTextI18n")
    private fun getProfileSettings() {
        blockInput(pbProfile)
        profileSettingViewModel.getProfileSettings(
            ProfileSettingRequest(userId = "1"),
            onSuccess = {
                unblockInput(pbProfile)
                println("OUTPUTTT" + it.detail)
                ivProfilePic.loadCircularImage(it.detail.profile_image)
                tvProfileName.text = it.detail.name
                if (it.detail.keywordswords.isNullOrEmpty()) {
                    tvTag.visibility = View.GONE
                } else {
                    tvTag.text = it.detail.keywordswords
                }

                tvProfilePhone.text =
                    it.detail.country_code.toString() + " " + it.detail.phone_no.toString()
                tvProfileMail.text = it.detail.email
                help_page = it.detail.help_page
                about = it.detail.about
                terms_condition = it.detail.terms_condition
                privacy_policy = it.detail.privacy_policy
                invite_message = it.detail.invite_message
                invite_url = it.detail.invite_url
            },
            onError = {
                unblockInput(pbProfile)
                activity?.toast(it)
            }
        )
    }


    fun bottomSheet() {

        if (bottomSheetFragment == null) {
            bottomSheetFragment = MySpaceBottomFragment()
            bottomSheetFragment?.show(childFragmentManager, bottomSheetFragment?.tag)
        } else {
            bottomSheetFragment?.show(childFragmentManager, bottomSheetFragment?.tag)
        }

    }

}