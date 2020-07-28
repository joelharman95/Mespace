package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ClosestToRequest(
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("longitude") val longitude: String? = "",
    @SerializedName("latitude") val latitude: String? = ""
)