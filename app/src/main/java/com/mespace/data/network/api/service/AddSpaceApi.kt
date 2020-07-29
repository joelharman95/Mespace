package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.ReqAddStore
import com.mespace.data.network.api.request.ReqIsHomePageExists
import com.mespace.data.network.api.response.AddStoreResponse
import com.mespace.data.network.api.response.CategoryResponse
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.utility.API.Add_new_store
import com.mespace.di.utility.API.Category_list_Api

import com.mespace.di.utility.API.INDEX

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AddSpaceApi {
    @GET(INDEX)
    suspend fun getCategoryList(
            @Query("type") type: String = Category_list_Api
            ):Response<CategoryResponse>


    @POST(INDEX)
    suspend fun getAddNewStore(
            @Query("type") type: String = Add_new_store,
            @Body reqAddStore: ReqAddStore
    ): Response<AddStoreResponse>

}