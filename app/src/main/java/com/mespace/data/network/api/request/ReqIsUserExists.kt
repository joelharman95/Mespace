package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ReqIsUserExists(
    @SerializedName("country_code") val countryCode: String? = "",
    @SerializedName("phone") val phone: String? = ""
)
