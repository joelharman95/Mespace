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
import com.mespace.data.viewmodel.ProfileViewModel
import com.mespace.di.*
import com.mespace.di.utility.BundleConstants.COUNTRY_CODE
import com.mespace.di.utility.BundleConstants.PHONE_NUMBER
import com.mespace.di.utility.ImageConstants.CAMERA
import com.mespace.di.utility.ImageConstants.GALLERY
import kotlinx.android.synthetic.main.fragment_profile_setup.*
import kotlinx.android.synthetic.main.fragment_profile_setup1.btnUpdate
import kotlinx.android.synthetic.main.fragment_profile_setup1.cgTag
import kotlinx.android.synthetic.main.fragment_profile_setup1.ibBack
import kotlinx.android.synthetic.main.fragment_profile_setup1.ivEditDp
import kotlinx.android.synthetic.main.fragment_profile_setup1.ivProfile
import kotlinx.android.synthetic.main.fragment_profile_setup1.pbProfile
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProfileSetupFragment : Fragment(), LifecycleObserver {

    private val profileViewModel by viewModel<ProfileViewModel>()
    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001

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
            findNavController().navigate(R.id.action_profileSetupFragment_to_homeFragment)
        }

        etKeywords.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
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
                "7639989666"*/
                arguments?.getString(COUNTRY_CODE),
                arguments?.getString(PHONE_NUMBER)
            ), onSuccess = {
                unblockInput(pbProfile)
                it.userDetail?.let { userDetail ->
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
            }, onError = {
                unblockInput(pbProfile)
                activity?.toast(it)
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

    private fun requestRuntimePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
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
                    val result = getRealPathFromURI(data.data?.toString())
                    val file = File(result)
                }
                CAMERA -> {
                    val bitmap = data.extras?.get("data") as Bitmap
                    val imagePath = context?.savePic(bitmap)
                    ivProfile.loadCircularImage(bitmap)
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
        val chip = Chip(context)
        chip.text = "#$keyword"
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        cgTag.addView(chip as View)
        chip.setOnCloseIconClickListener { cgTag.removeView(chip as View) }
    }

}
