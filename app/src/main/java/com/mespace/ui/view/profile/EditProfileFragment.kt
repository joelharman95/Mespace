package com.mespace.ui.view.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.mespace.R
import com.mespace.data.network.api.request.ProfileSettingRequest
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.request.ReqUpdateUser
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.EditProfileViewModel

import com.mespace.di.*
import com.mespace.di.utility.BundleConstants
import com.mespace.di.utility.ImageConstants
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.ivCategoryBack
import kotlinx.android.synthetic.main.fragment_edit_profile.pbProfile
import kotlinx.android.synthetic.main.fragment_profile.*

import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class EditProfileFragment : Fragment(), LifecycleObserver {

    private val profileViewModel by viewModel<EditProfileViewModel>()

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    private var userId = ""
    private var mBitmap: Bitmap? = null
    private var keywordList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPhone.isEnabled = false

        ivCategoryBack.setOnClickListener {
            findNavController().navigateUp()
        }
        ivEditDp.setOnClickListener {
            onImagePicker()
        }
        btnUpdate.setOnClickListener {
            if (isFieldValid() && isEmailValid())
                addOrUpdateProfile()
        }

        etKeywords.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(etKeywords.text.toString())) {
                if (keywordList.size < 5) {
                    addChipToGroup(etKeywords.text.toString())
                    etKeywords.setText("")
                } else
                    activity?.toast(getString(R.string.label_more_keywords))
            }
            false
        }
        getProfileSettings()

    }

    private fun getProfileSettings() {
        blockInput(pbProfile)
        profileViewModel.getProfileSettings(
            ProfileSettingRequest(userId = "1"),
            onSuccess = {
                unblockInput(pbProfile)
                userId = it.detail.user_id.toString()
                etName.setText(it.detail.name?.toString())
                etEmail.setText(it.detail.email?.toString())
                ivProfile.loadCircularImage(it.detail.profile_image.toString())
                etPhone.setText(it.detail.phone_no)
                val keywords = it.detail.keywords?.split(",")
                keywords?.forEach { tag ->
                    addChipToGroup(tag)
                }
            },
            onError={
                unblockInput(pbProfile)
                activity?.toast(it)
            })
    }



    private fun addChipToGroup(keyword: String) {
        if (keyword.contains("#")) {
            val parts = keyword.split("#")
            val removedEmpty = parts.filter { it != "" }
            removedEmpty.forEach {
                val removedComma = it.removeSuffix(",")
                if (keywordList.size < 5) {
                    keywordList.add(removedComma)
                    createChips(removedComma)
                } else
                    activity?.toast(getString(R.string.label_more_keywords))
            }
        } else {
            keywordList.add(keyword)
            createChips(keyword)
        }
    }

    private fun createChips(keyWords: String) {
        val chip = Chip(context)
        chip.text = "#$keyWords"
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        cgTag.addView(chip as View)
        chip.setOnCloseIconClickListener {
            val new = chip.text.removePrefix("#")
            keywordList.remove(new)
            cgTag.removeView(chip as View)
        }
    }


    private fun addOrUpdateProfile() {
        activity?.blockInput(pbProfile)

        var mobileNo = ""
        var code = ""
        PreferenceManager(requireContext()).apply {
            code = getCountryCode()
            mobileNo = getPhoneNumber()

        }

        var keywords = ""
        keywordList.forEach {
            keywords += "$it,"
        }
        profileViewModel.addOrUpdateProfile(
            reqUpdateUser = ReqUpdateUser(
                /*countryCode = "91",
                phone = "7639989667",*/
                countryCode = code,
                phone = mobileNo,
                keywords = keywords.removeSuffix(","),
                profileImage = bitMapToString(mBitmap),
                userId = userId,
                /*userName = "ndot",
                email = "ndottt@ndot.in"*/
                userName = etName.text.toString(),
                email = etEmail.text.toString()

            ), onSuccess = {
                activity?.unblockInput(pbProfile)
                PreferenceManager(requireContext()).apply {
                    setUserId(it.detail?.user_id)
                    setUserName(it.detail?.name)
                    setUserProfile(it.detail?.profileImage)
                }
                activity?.toast(it.message.toString())
            }, onError = {
                activity?.unblockInput(pbProfile)
                activity?.toast(it)
                println("jkdfulksdghf"+it)
            }
        )
    }


    private fun onImagePicker() {
        if (checkRuntimePermission()) {
            this.showDialogToPick()
        } else {
            requestRuntimePermission()
        }
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
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            ), READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    private fun isFieldValid(): Boolean {
        return when {
            TextUtils.isEmpty(etName.text.toString()) -> {
                activity?.toast(getString(R.string.username_required))
                false
            }
            TextUtils.isEmpty(etEmail.text.toString()) -> {
                activity?.toast(getString(R.string.email_address_required))
                false
            }
            else -> true
        }
    }

    private fun isEmailValid(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (!etEmail.text.toString().trim().matches(emailPattern.toRegex())) {
            activity?.toast(getString(R.string.valid_email_required))
            false
        } else
            true
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
                    true -> this.showDialogToPick()  //  openImagePicker()
                    else -> activity?.toast(resources.getString(R.string.permission_denied))
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ImageConstants.GALLERY -> {
                    val selectedImage = data.data
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(
                            context?.contentResolver!!,
                            selectedImage
                        )
                    ivProfile.loadCircularImage(bitmap)
                    mBitmap = bitmap
                    val result = getRealPathFromURI(data.data?.toString())
                    val file = File(result)
                }
                ImageConstants.CAMERA -> {
                    val bitmap = data.extras?.get("data") as Bitmap
                    val imagePath = context?.savePic(bitmap)
                    ivProfile.loadCircularImage(bitmap)
                    mBitmap = bitmap
                }
            }
        }
    }

    private fun getRealPathFromURI(contentURI: String?): String? {
        val contentUri = Uri.parse(contentURI)
        val cursor: Cursor? =
            activity?.contentResolver?.query(contentUri, null, null, null, null)
        return if (cursor == null) contentUri.path else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }


}