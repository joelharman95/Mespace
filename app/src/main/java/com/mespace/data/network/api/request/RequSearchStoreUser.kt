package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName




data class RequSearchStoreUser(
        @SerializedName("latitude") val latitude: String? = "",
        @SerializedName("longitude") val longitude: String? = "",
        @SerializedName("start") val start: String? = "",
        @SerializedName("limit") val limit: String? = "",
        @SerializedName("keyword") val keyword: String? = "",
        @SerializedName("category_id") val category_id: String? = ""
)