package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class NearByStoreRequest (
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("longitude") val longitude: String? = "",
    @SerializedName("latitude") val latitude: String? = "",
    @SerializedName("start") val start:Int?=0,
    @SerializedName("limit") val limit:String?="0"
)