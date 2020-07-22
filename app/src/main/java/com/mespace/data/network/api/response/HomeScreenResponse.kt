package com.mespace.data.network.api.response

data class HomeScreenResponse(
    val detail: Detail,
    val message: String,
    val status: Int
) {
    data class Detail(
        val closest_users: List<ClosestUser>,
        val mespace_list: List<Mespace>,
        val storelist: List<Storelist>,
        val userlist: List<Userlist>
    ) {
        data class ClosestUser(
            val country_code: String,
            val distance: String,
            val name: String,
            val phone: String,
            val profile_image: String,
            val user_id: String
        )

        data class Mespace(
            val country_code: String,
            val name: String,
            val phone: String,
            val profile_image: String,
            val space_id: String
        )

        data class Storelist(
            val country_code: String,
            val distance: String,
            val name: String,
            val phone: String,
            val profile_image: String,
            val space_id: String
        )

        data class Userlist(
            val categories_name: String,
            val close_hours: String,
            val name: String,
            val open_hours: String,
            val profile_image: String,
            val tag_name: String,
            val type: String,
            val user_id: String
        )
    }
}