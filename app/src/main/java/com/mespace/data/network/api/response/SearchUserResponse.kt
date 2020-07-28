package com.mespace.data.network.api.response

data class SearchUserResponse(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val user_list: List<Userlist>
    ) {
        data class Userlist(
            val country_code: String,
            val distance: String,
            val keywords: List<String>,
            val name: String,
            val phone_no: String,
            val profile_image: String,
            val user_id: String,
            val type: String,
            val tag_name: String,
            val open_hours: String,
            val close_hours: String,
            val categories_name: String,
            val open_close:String,
            val description:String
        )
    }
}