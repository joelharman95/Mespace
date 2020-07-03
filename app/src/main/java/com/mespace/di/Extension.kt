/*
 * *
 *  * Created by Nethaji on 27/6/20 1:18 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 12:32 PM
 *
 */

package com.mespace.di

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mespace.di.utility.Constants.IMAGE_DIRECTORY
import com.mespace.di.utility.Constants.IMAGE_FROM_CAMERA
import com.mespace.di.utility.Constants.IMAGE_FROM_GALLERY
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun Context.getCompatFont(@FontRes fontRes: Int): Typeface =
    ResourcesCompat.getFont(this, fontRes) ?: Typeface.DEFAULT

fun View.getCompatSize(@DimenRes dimenRes: Int): Int =
    resources.getDimension(dimenRes).toInt()

fun View.getCompatColor(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(this.context, colorRes)

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadCircularImage(image: Any) {
    Glide.with(context)
        .load(
            when (image) {
                is Int -> ContextCompat.getDrawable(context, image)
                is Bitmap -> image
                else -> image
            }
        )
        .circleCrop()
        .into(this)
}

fun Fragment.showDialogToPick() {
    val pickDialog = AlertDialog.Builder(this.context)
    pickDialog.setTitle("Choose an action")
    val pictureSelection = arrayOf("Select image from gallery", "Take photo on camera")
    //val pictureSelection = arrayOf("Take photo on camera")
    pickDialog.setItems(pictureSelection) { dialog, which ->
        when (which) {
            0 -> choosePhotoFromGallery()
            1 -> takePhotoFromCamera()
        }
    }
    pickDialog.setOnCancelListener {}
    pickDialog.show()
}

typealias result = (Boolean) -> Unit

fun Activity.showDialAlert(title: String, result: result) {
    val exitDialog = androidx.appcompat.app.AlertDialog.Builder(this)
    exitDialog.setMessage(title)
    exitDialog.setNegativeButton(
        "no"
    ) { dialog, which ->
        dialog.dismiss()
    }
    exitDialog.setPositiveButton(
        "yes"
    ) { dialog, which ->
        result(true)
        dialog.dismiss()
        dialog.cancel()
    }
    exitDialog.show()
}

fun Activity.showDialAlertSingle(title: String) {
    val exitDialog = androidx.appcompat.app.AlertDialog.Builder(this)
    exitDialog.setMessage(title)
    exitDialog.setPositiveButton(
        "OK"
    ) { dialog, which ->
        dialog.dismiss()
        dialog.cancel()
    }
    exitDialog.show()
}


fun Fragment.choosePhotoFromGallery() {
    startActivityForResult(
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
        IMAGE_FROM_GALLERY
    )
}

fun Fragment.takePhotoFromCamera() {
    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), IMAGE_FROM_CAMERA)
}

fun Context.savePic(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
    //  val directory = File(Environment.getExternalStorageState().toString() + IMAGE_DIRECTORY)  // getExternalStorageDirectory()
    val directory = File(this.filesDir, IMAGE_DIRECTORY)
    if (!directory.exists()) directory.mkdirs()
    try {
        val file = File(directory, ((Calendar.getInstance().timeInMillis).toString() + ".jpg"))
        file.createNewFile()
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        MediaScannerConnection.scanFile(this, arrayOf(file.path), arrayOf("image/jpeg"), null)
        fileOutputStream.close()
        //  this.toast("Image set successful")
        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun bitMapToFile(bitmapImage: Bitmap, context: Context): File {
    var file: File? = null
    try {
        val f =
            File(context.getCacheDir(), (Calendar.getInstance().timeInMillis).toString() + ".jpg")
        f.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 70, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        file = f
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return file!!
}

fun getBitmapFromURL(src: String?): Bitmap? {
    return try {
        val url = URL(src)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun BitMapToString(userImage1: Bitmap): String {
    val baos = ByteArrayOutputStream()
    userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos)
    val b = baos.toByteArray()
    return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
}

public fun toRequestBody(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun dismissKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (null != activity.currentFocus)
        imm.hideSoftInputFromWindow(activity.currentFocus!!.applicationWindowToken, 0)
}

fun dismissKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.blockInput(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
    activity?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.unblockInput(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.blockInput(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.unblockInput(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.unblockInput() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun isConnected(activity: Activity): Boolean {
    val connectivityManager =
        activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null
}

fun String.isSuccess(): Boolean = this == "1"

fun String.isFailure(): Boolean = this == "failure"

fun stingChangeInToLong(number: String): Long {
    val stt = number.replace("\\D+".toRegex(), "")
    return stt.toLong()
}