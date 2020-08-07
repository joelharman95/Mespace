package com.mespace.data.network.api.response

data class StoreDetailsResponse (val message : String,
                            val status : Int,
                            val store_detail : Store_detail){
    data class Store_detail (

        val id : Int,
        val name : String,
        val location : String,
        val keywords : String,
        val open_hours : String,
        val close_hours : String,
        val website : String,
        val country_code : Int,
        val phone_no : String,
        val category_info : List<Category_info>,
        val description : String,
        val ratings : Int,
        val favourited_users : List<Int>,
        val is_favourite : String,
        val profile_image : String,
        val open_close:String,
        val user_profile_image:String
    ){
        data class Category_info (

            val category_id : Int,
            val category_name : String,
            val category_image : String
        )
    }
}