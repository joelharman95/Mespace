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
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.mespace.R
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.request.ReqUpdateUser
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.ProfileViewModel
import com.mespace.di.*
import com.mespace.di.utility.BundleConstants.COUNTRY_CODE
import com.mespace.di.utility.BundleConstants.PHONE_NUMBER
import com.mespace.di.utility.ImageConstants.CAMERA
import com.mespace.di.utility.ImageConstants.GALLERY
import kotlinx.android.synthetic.main.fragment_profile_setup.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProfileSetupFragment : Fragment(), LifecycleObserver {

    private val profileViewModel by viewModel<ProfileViewModel>()
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    private var userId = ""
    private var mBitmap: Bitmap? = null
    private var keywordList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibBack.setOnClickListener {
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
                println("Out_put"+ " "+ etKeywords.text.toString())
                addChipToGroup(etKeywords.text.toString())
                etKeywords.setText("")
            }
            false
        }
        isUserExists()

    }

    private fun isUserExists() {
        blockInput(pbProfile)
        profileViewModel.isUserExists(
            ReqIsUserExists(
                /*"91",
                "7639989667"*/
                arguments?.getString(COUNTRY_CODE),
                arguments?.getString(PHONE_NUMBER)
            ), onSuccess = {
                unblockInput(pbProfile)
                if (it.userDetails == "1") {
                    it.userDetail?.let { userDetail ->
                        PreferenceManager(requireContext()).apply {
                            setUserId(userDetail.userId)
                            setUserName(userDetail.name)
                            setUserProfile(userDetail.profileImage)
                        }
                        userId = userDetail.userId.toString()
                        etName.setText(userDetail.name?.toString())
                        etEmail.setText(userDetail.email?.toString())
                        ivProfile.loadCircularImage(userDetail.profileImage.toString())
                        val keywords = userDetail.keywords?.split(",")
                        keywords?.forEach { tag ->
                            addChipToGroup(tag)
                        }
                        /*userDetail.keywords?.forEach { keywords ->
                            addChipToGroup(keywords.toString())
                        }*/
                    }
                }
            }, onError = {
                unblockInput(pbProfile)
                activity?.toast(it)
            }
        )
    }

    private fun addOrUpdateProfile() {
        activity?.blockInput(pbProfile)
        var keywords = ""
        keywordList.forEach {
            keywords += "$it,"
        }
        profileViewModel.addOrUpdateProfile(
            reqUpdateUser = ReqUpdateUser(
                /*countryCode = "91",
                phone = "7639989667",*/
                arguments?.getString(COUNTRY_CODE),
                phone = arguments?.getString(PHONE_NUMBER),
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
                    setUserId(it.detail?.passengerId)
                    setUserName(it.detail?.name)
                    setUserProfile(it.detail?.profileImage)
                }
                findNavController().navigate(R.id.action_profileSetupFragment_to_homeFragment)
            }, onError = {
                activity?.unblockInput(pbProfile)
                activity?.toast(it)
            }
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

    private fun onImagePicker() {
        if (checkRuntimePermission()) {
            this.showDialogToPick()
        } else {
            requestRuntimePermission()
        }
    }

    private fun requestRuntimePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
            ), READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
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
                GALLERY -> {
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
                CAMERA -> {
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

    private fun addChipToGroup(keyword: String) {

        if(keyword.contains("#"))
        {

            val parts = keyword.split("#")
            println("Out_put"+ " "+ parts[0] + " "+ parts[1])
            val chip = Chip(context)
            keywordList.add(parts[0])
            keywordList.add(parts[1])



            for(i in keywordList)
            {
                chip.text = "#$i"
            }
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
        else{
            val chip = Chip(context)
            keywordList.add(keyword)
            chip.text = "#$keyword"
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


    }

}
