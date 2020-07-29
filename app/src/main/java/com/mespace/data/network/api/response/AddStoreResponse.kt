package com.mespace.data.network.api.response

data class AddStoreResponse(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val profile_image: String,
        val space_id: String,
        val space_name: String,
        val space_owner_id: Int,
        val space_owner_name: String,
        val space_type: String,
        val tag_name: String
    )
}