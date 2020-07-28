package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class MyUserRequest(
    @SerializedName("user_id") val userId: String? = ""
)