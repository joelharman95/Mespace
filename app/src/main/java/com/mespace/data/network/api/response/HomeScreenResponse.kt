package com.mespace.data.network.api.response

data class HomeScreenResponse(
    val detail: Detail,
    val message: String,
    val status: String
) {
    data class Detail(
        val mespace_list: List<Mespace>,
        val storelist: List<Storelist>,
        val userlist: List<Userlist>
    ) {
        data class Mespace(
            val mespace_id: String,
            val name: String,
            val profile_image: String
        )

        data class Storelist(
            val country_code: String,
            val distance: String,
            val keywords: List<String>,
            val name: String,
            val phone_no: String,
            val profile_image: String,
            val space_id: String
        )

        data class Userlist(
            val country_code: String,
            val distance: String,
            val keywords: List<String>,
            val name: String,
            val phone_no: String,
            val profile_image: String,
            val user_id: String
        )
    }
}