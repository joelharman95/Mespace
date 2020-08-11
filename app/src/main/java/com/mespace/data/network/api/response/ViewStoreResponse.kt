package com.mespace.data.network.api.response

data class ViewStoreResponse(
    val message: String,
    val space_detail: SpaceDetail,
    val status: Int
)