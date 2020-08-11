package com.mespace.data.network.api.response

data class SpaceDetail(
    val category_info: List<CategoryInfo>,
    val close_hours: String,
    val country_code: String,
    val description: String,
    val favourited_users: List<Any>,
    val favourited_users_count: Int,
    val id: Int,
    val is_favourite: Int,
    val is_owner: String,
    val keywords: String,
    val location: String,
    val name: String,
    val open_close: String,
    val open_hours: String,
    val phone_no: String,
    val profile_image: String,
    val ratings: Int,
    val space_owner_id: Int,
    val user_profile_image: String,
    val website: String
)