package com.mespace.data.network.api.response

import com.google.gson.annotations.SerializedName

data class ResUserUpdate(
    @SerializedName("detail") val detail: List<DetailItem?>? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Int? = null
)

data class DetailItem(
    @SerializedName("country_code") val countryCode: String? = null,
    @SerializedName("phone_no") val phoneNo: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("keywords") val keywords: String? = null,
    @SerializedName("passenger_id") val passengerId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("status") val status: String? = null
)
