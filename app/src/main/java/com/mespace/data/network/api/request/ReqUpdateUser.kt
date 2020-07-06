package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ReqUpdateUser(
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("keywords") val keywords: String? = null,
    @SerializedName("user_id") val userId: String = "",
    @SerializedName("user_name") val userName: String? = null,
    @SerializedName("email") val email: String? = null
)