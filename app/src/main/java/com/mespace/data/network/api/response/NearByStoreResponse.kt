package com.mespace.data.network.api.response

data class NearByStoreResponse (
    val message : Int,
    val status : Int,
    val detail : Detail
){
    data class Detail (
        val store_list : List<Store_list>
    ){
        data class Store_list (
            val distance : String,
            val space_id : Int,
            val name : String,
            val country_code : Int,
            val phone : String,
            val profile_image : String,
            val tag_name : String,
            val categories : String,
            val description : String,
            val open_close:String,
            val open_hours:String,
            val close_hours:String
        )
    }
}

