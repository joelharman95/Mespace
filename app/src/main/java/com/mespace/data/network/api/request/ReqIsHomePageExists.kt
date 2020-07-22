package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ReqIsHomePageExists(
    @SerializedName("user_id") val userid: String? = "",
    @SerializedName("latitude") val latitude: String? = "",
    @SerializedName("longitude") val longitude: String? = ""
)
