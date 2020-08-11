package com.mespace.ui.view.addaspace

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.mespace.R
import com.mespace.data.network.api.request.ReqAddStore
import com.mespace.data.network.api.request.ReqUpdateStore
import com.mespace.data.network.api.request.ReqViewStore
import com.mespace.data.network.api.response.CategoryResponse
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.AddSpaceViewModel
import com.mespace.data.viewmodel.EditSpaceViewModel
import com.mespace.di.*
import com.mespace.di.utility.ImageConstants
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_space.*
import kotlinx.android.synthetic.main.fragment_edit_space.etPhone
import kotlinx.android.synthetic.main.fragment_edit_space.ibAddSpaceBack
import kotlinx.android.synthetic.main.fragment_edit_space.ivEditDp
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class  EditSpaceFragment : Fragment(), LifecycleObserver {


    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    private var selectedSpace_type: String = ""
    private var mBitmap: Bitmap? = null
    private var categoryList = mutableListOf<String>()
    private val addSpaceViewModel by viewModel<EditSpaceViewModel>()
    private var keywordList = mutableListOf<String>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var asLatitude: String = ""
    var asLongitude: String = ""
    private val checkedCategoryList: HashMap<Int, String> = HashMap()
    var userid :String = ""
    var userName :String = ""
    var store_id :String = ""
    var profile_image :String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_space, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PreferenceManager(requireContext()).apply {
            userid = getUserId()
            userName = getUserName()
            store_id = getStoreId()
            println("Get_user_id" + " "+ userid)
        }
        getStoreDetails()

        if (!checkRuntimePermission()) {
            requestRuntimePermission()
        }
        ibAddSpaceBack.setOnClickListener {
            findNavController().navigateUp()
        }
        ivEditDp.setOnClickListener {
            onImagePicker()
        }

        llopen_hour.setOnClickListener {
            var am_pm: String = ""
            var time: Int = 0
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                if (cal.get(Calendar.AM_PM) == Calendar.AM) {
                    am_pm = "am"
                } else if (cal.get(Calendar.AM_PM) == Calendar.PM) {
                    am_pm = "pm"
                }

                open_hour.text = SimpleDateFormat("hh:mm").format(cal.time) + " " + am_pm


            }
            TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()


        }
        llclose_hour.setOnClickListener {
            var am_pm: String = ""
            var time: Int = 0

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                if (cal.get(Calendar.AM_PM) == Calendar.AM) {
                    am_pm = "am"
                } else if (cal.get(Calendar.AM_PM) == Calendar.PM) {
                    am_pm = "pm"
                }




                close_hour.setText(SimpleDateFormat("hh:mm").format(cal.time) + " " + am_pm)

            }

            TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()

        }

        cancel.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        asKeywords.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(asKeywords.text.toString())) {
                if (keywordList.size < 5) {
                    addChipToGroup(asKeywords.text.toString())
                    asKeywords.setText("")
                } else
                    activity?.toast(getString(R.string.label_more_keywords))
            }
            false
        }

        btnEditSpace.setOnClickListener {
            if (store.isChecked()) {
                selectedSpace_type = "S";
            } else if (community.isChecked()) {
                selectedSpace_type = "C";
            }

            if (mBitmap == null ) {
                if(profile_image.equals(""))
                {
                    activity?.toast("Kindly upload profile image")
                }
                else{
                    if (isFieldValid()) {
                        addNewStore()
                    }
                }

            } else {
                if (isFieldValid()) {
                    addNewStore()
                }

            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        showLocation()

    }

    private fun getStoreDetails() {
        addSpaceViewModel.viewSpaceDetils(ReqViewStore(space_id = store_id,user_id = userid),{
            etPhone.setText( it.space_detail.phone_no)
            asName.setText( it.space_detail.name)
            open_hour.setText(it.space_detail.open_hours)
            close_hour.setText(it.space_detail.close_hours)
            etLocation.setText(it.space_detail.location)
            asDescription.setText(it.space_detail.description)
            ivEditSpaceProfile.loadCircularImage(it.space_detail.profile_image.toString())

            profile_image = it.space_detail.profile_image.toString()
            val keywords = it.space_detail.keywords?.split(",")

            keywords?.forEach { tag ->
                addChipToGroup(tag)
            }
        },{
            println("Out_put_array"+ " "+ it)
        })
    }

    private fun addNewStore() {


        var keywords = ""
        keywordList.forEach {
            keywords += "$it,"
        }

        var catWords = ""
        checkedCategoryList.forEach {
            catWords=catWords+it.value+","
        }
        /*categoryList.forEach {
            catWords += "$it,"
        }*/
        blockInput(asLoader)
        addSpaceViewModel.updateSpace(

            ReqUpdateStore(
                country_code = "+91",
                phone = etPhone.text.toString(),
                space_name = asName.text.toString(),
                /*  latitude = asLatitude,
                  longitude = asLongitude,*/
                latitude = "11.05617456",
                longitude = "77.0185673",
                keywords = keywords.removeSuffix(","),
                open_time = open_hour.text.toString(),
                close_time = close_hour.text.toString(),
                location = etLocation.text.toString(),
                description = asDescription.text.toString(),
                user_id = userid,
                user_name = userName,
                categories = catWords.removeSuffix(","),

                profile_image = bitMapToString(mBitmap),
                website = "dsfdsf",
                space_type = selectedSpace_type,
                space_id = store_id//Static
            ), {
                activity?.toast(it.message)
                findNavController().navigate(R.id.homeFragment)
                unblockInput(asLoader)
            }, {
                activity?.toast(it)
                unblockInput(asLoader)
            }
        )
    }


    private fun getcategoryList() {
        blockInput(asLoader)
        addSpaceViewModel.getCategoryList({
//            (category_list.adapter as CategoryAdapter).addCategoryList(it.detail)
            for (item in it.detail) {
                categoryList.add(item.category_name)
            }
            setChipsInCategory(it.detail)
            unblockInput(asLoader)

        }, {
            unblockInput(asLoader)
            activity?.toast(it)
        })
    }

    private fun setChipsInCategory(list: List<CategoryResponse.Detail>) {
        for (item in list) createCategoryChips(item)
    }

    private fun createCategoryChips(category: CategoryResponse.Detail) {
        val chip = Chip(context)
        chip.text = category.category_name
        chip.id = category.category_id.toInt()
        chip.isCloseIconVisible = false
        chip.isClickable = true
        chip.chipStrokeWidth = 5f
        chip.isCheckedIconVisible = false


        val chipDrawable: ChipDrawable? = context?.let {
            ChipDrawable.createFromAttributes(
                it,
                null,
                0,
                R.style.ChipsStyle
            )
        }
        if (chipDrawable != null) {
            chip.setChipDrawable(chipDrawable)
        }
        categoryChip.addView(chip as View)
        chip.setOnCheckedChangeListener { chipView, isChecked ->
            if (isChecked) {
                checkedCategoryList[chipView.id] = chipView.text.toString()
            }
            else{
                if(checkedCategoryList.containsKey(chipView.id)){
                    checkedCategoryList.remove(chipView.id)
                }
            }
        }
        chip.setOnCloseIconClickListener {

            /*val new = chip.text.removePrefix("#")
            keywordList.remove(new)
            adTag.removeView(chip as View)*/


        }

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
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
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
                    true -> showLocation() //  openImagePicker()
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
        adTag.addView(chip as View)
        chip.setOnCloseIconClickListener {
            val new = chip.text.removePrefix("#")
            keywordList.remove(new)
            adTag.removeView(chip as View)
        }
    }


    private fun isFieldValid(): Boolean {
        return when {
            TextUtils.isEmpty(asName.text.toString()) -> {
                activity?.toast(getString(R.string.space_required))
                false
            }


            TextUtils.isEmpty(etPhone.text.toString()) -> {
                activity?.toast(getString(R.string.label_phone_no_empty))
                false
            }


            TextUtils.isEmpty(open_hour.text.toString()) -> {
                activity?.toast(getString(R.string.label_description_message))
                false
            }


            TextUtils.isEmpty(close_hour.text.toString()) -> {
                activity?.toast(getString(R.string.label_description_message))
                false
            }


            TextUtils.isEmpty(asDescription.text.toString()) -> {
                activity?.toast(getString(R.string.label_description_message))
                false
            }


            else -> true
        }
    }

    /*private fun isEmailValid(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (!etEmail.text.toString().trim().matches(emailPattern.toRegex())) {
            activity?.toast(getString(R.string.valid_email_required))
            false
        } else
            true
    }*/

    fun showLocation() {
        /*fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    try {
                        val geocoder = Geocoder(requireContext())
                        val addressList =
                                geocoder.getFromLocation(location!!.latitude, location.longitude, 1)

                        asLatitude = location!!.latitude.toString()
                        asLongitude = location.longitude.toString()
                        if (addressList != null && addressList.size > 0) {
                            val country = addressList[0].getAddressLine(0)
                            etLocation.setText(country)

                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }finally {
                        getcategoryList()
                    }
                }*/
        getcategoryList()
    }



}