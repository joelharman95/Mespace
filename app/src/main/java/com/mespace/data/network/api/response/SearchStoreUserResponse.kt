package com.mespace.data.network.api.response

data class SearchStoreUserResponse(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val storelist: List<Storelist>
    ) {
        data class Storelist(
            val close_hours: String,
            val country_code: String,
            val distance: String,
            val keywords: List<String>,
            val name: String,
            val open_hours: String,
            val phone_no: String,
            val profile_image: String,
            val space_id: String
        )
    }
}