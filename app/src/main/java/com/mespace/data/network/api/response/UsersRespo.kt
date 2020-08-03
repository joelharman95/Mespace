package com.mespace.data.network.api.response

data class UsersRespo(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val users_list: List<Users>
    ) {
        data class Users(
            val country_code: String,
            val distance: String,
            val keywords: String,
            val name: String,
            val phone: String,
            val profile_image: String,
            val user_id: String
        )
    }
}