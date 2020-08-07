/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:41 PM
 *
 */

package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.AddFavouriteRequest
import com.mespace.data.network.api.request.BusinessDetailsRequest
import com.mespace.data.network.api.request.DeleteFavouriteRequest
import com.mespace.data.network.api.request.NearByStoreRequest
import com.mespace.data.network.api.response.AddFavouriteResponse
import com.mespace.data.network.api.response.DeleteFavouriteResponse
import com.mespace.data.network.api.response.NearByStoreResponse
import com.mespace.data.network.api.response.StoreDetailsResponse
import com.mespace.di.utility.API.ADD_FAV
import com.mespace.di.utility.API.GET_NEARBY_STORE_LIST
import com.mespace.di.utility.API.INDEX
import com.mespace.di.utility.API.VIEW_STORE_DETAILS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NearestStoreList {

    @POST(INDEX)
    suspend fun getStoreList(
        @Query("type") type: String = GET_NEARBY_STORE_LIST,
        @Body nearByStoreRequest: NearByStoreRequest
    ): Response<NearByStoreResponse>


    @POST(INDEX)
    suspend fun getStoreDetails(
        @Query("type") type:String=VIEW_STORE_DETAILS,
        @Body businessDetailsRequest: BusinessDetailsRequest
    ): Response<StoreDetailsResponse>

    @POST(INDEX)
    suspend fun addFavourite(
        @Query("type")type: String=ADD_FAV,
        @Body addFavouriteRequest: AddFavouriteRequest
    ):Response<AddFavouriteResponse>

    @POST(INDEX)
    suspend fun deleteFavourites(
        @Query("type")type:String="delete_favourite",
        @Body deleteFavouriteRequest: DeleteFavouriteRequest
    ):Response<DeleteFavouriteResponse>

}