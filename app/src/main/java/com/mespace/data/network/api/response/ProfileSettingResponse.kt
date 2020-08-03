package com.mespace.data.network.api.response

data class ProfileSettingResponse (

    val message : String,
    val detail : Detail,
    val status : Int
){
    data class Detail (

        val passenger_id : Int,
        val country_code : Int,
        val phone_no : String,
        val name : String,
        val email : String,
        val status : String,
        val keywordswords : String,
        val profile_image : String,
        val user_id : Int,
        val help_page : String,
        val about : String,
        val terms_condition : String,
        val privacy_policy : String,
        val invite_message : String,
        val invite_url : String
    )
}

