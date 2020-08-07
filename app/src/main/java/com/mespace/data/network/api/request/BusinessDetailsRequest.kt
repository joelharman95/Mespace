package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class BusinessDetailsRequest(
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("store_id") val storeId: String? = ""
)