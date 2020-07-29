package com.mespace.data.network.api.response

data class CategoryResponse(
    val detail: List<Detail>,
    val message: String,
    val status: Int
) {
    data class Detail(
        val category_id: String,
        val category_name: String
    )
}