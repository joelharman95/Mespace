package com.mespace.data.network.api.response

class MySpaceResponse(
    val detail : Detail,
    val message : String,
    val status : Int

){
    data class Detail (

        val my_space : List<My_space>
    ){
        data class My_space (

            val space_id : Int,
            val name : String,
            val country_code : Int,
            val phone : String,
            val space_type : String,
            val profile_image : String
        )
    }
}
