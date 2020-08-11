package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ReqAddStore(
    @SerializedName("country_code") val country_code: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("space_name") val space_name: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String = "",
    @SerializedName("keywords") val keywords: String? = null,
    @SerializedName("open_time") val open_time: String? = null,
    @SerializedName("close_time") val close_time: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("description") val description: String = "",
    @SerializedName("user_id") val user_id: String? = null,
    @SerializedName("user_name") val user_name: String? = null,
    @SerializedName("categories") val categories: String? = null,
    @SerializedName("profile_image") val profile_image: String = "",
    @SerializedName("website") val website: String? = null,
    @SerializedName("space_type") val space_type: String? = null
)


data class ReqUpdateStore(
    @SerializedName("country_code") val country_code: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("space_name") val space_name: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String = "",
    @SerializedName("keywords") val keywords: String? = null,
    @SerializedName("open_time") val open_time: String? = null,
    @SerializedName("close_time") val close_time: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("description") val description: String = "",
    @SerializedName("user_id") val user_id: String? = null,
    @SerializedName("user_name") val user_name: String? = null,
    @SerializedName("categories") val categories: String? = null,
    @SerializedName("profile_image") val profile_image: String = "",
    @SerializedName("website") val website: String? = null,
    @SerializedName("space_type") val space_type: String? = null,
    @SerializedName("space_id") val space_id:String?=null
)

data class ReqViewStore(
    @SerializedName("space_id") val space_id: String? = null,
    @SerializedName("user_id") val user_id: String? = null
)