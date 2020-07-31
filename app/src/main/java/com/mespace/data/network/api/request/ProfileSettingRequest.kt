package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class ProfileSettingRequest(
    @SerializedName("user_id") val userId: String? = ""
)