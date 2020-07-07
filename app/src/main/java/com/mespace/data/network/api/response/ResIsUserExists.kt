package com.mespace.data.network.api.response

import com.google.gson.annotations.SerializedName

data class ResIsUserExists(
    val message: String? = null,
    @SerializedName("userdetail") val userDetail: UserDetail? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("userdetails") val userDetails: String? = null
)

data class UserDetail(
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("phone_no") val phoneNo: String? = null,
    val name: String? = null,
    val email: String? = null,
    val status: String? = null,
    val keywords: String? = null,
    //  val keywords: List<String?>? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("user_id") val userId: String? = null
)


