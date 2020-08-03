package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName




data class RequSearchUser(

        @SerializedName("longitude") val longitude: String? = "",
        @SerializedName("latitude") val latitude: String? = "",
        @SerializedName("start") val start: String? = "",
        @SerializedName("limit") val limit: String? = "",
        @SerializedName("keyword") val keyword: String? = ""

)