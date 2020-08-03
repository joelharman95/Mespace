package com.mespace.data.network.api.response

data class StoreUserSearchResponse(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val category_list: List<Category>,
        val users_list: List<Users>
    ) {
        data class Category(
            val category_id: String,
            val category_name: String
        )

        data class Users(
            val close_hours: String,
            val country_code: String,
            val description: String,
            val distance: String,
            val keywords: String,
            val name: String,
            val open_hours: String,
            val phone: String,
            val profile_image: String,
            val user_id: String
        )
    }
}