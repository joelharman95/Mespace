package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

class MySpaceRequest(
    @SerializedName("user_id") val userId: String? = "",
    @SerializedName("start") val start: Int? = 0,
    @SerializedName("limit") val limit: String? = "0"
)