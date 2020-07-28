package com.mespace.data.network.api.response

data class ClosestToResponse(
    val message: String,
    val detail: Detail,
    val status: Int
) {
    data class Detail(

        val users_list: List<Users_list>
    ) {
        data class Users_list(

            val distance: String,
            val user_id: String,
            val name: String,
            val country_code: String,
            val phone: String,
            val profile_image: String,
            val tag_name: String
        )

    }
}

