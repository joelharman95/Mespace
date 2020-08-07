package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

data class AddFavouriteRequest(  @SerializedName("user_id") val userId: String? = "",
                                 @SerializedName("space_id") val spaceId: String? = "",
                                 @SerializedName("fav_user_id") val favUserId: String? = "0")

data class DeleteFavouriteRequest(  @SerializedName("user_id") val userId: String? = "",
                                 @SerializedName("space_id") val spaceId: String? = "",
                                 @SerializedName("fav_user_id") val favUserId: String? = "0")