/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:41 PM
 *
 */

package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.NearByStoreRequest
import com.mespace.data.network.api.response.NearByStoreResponse
import com.mespace.di.utility.API.GET_NEARBY_STORE_LIST
import com.mespace.di.utility.API.INDEX
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NearestStoreList {

    @POST(INDEX)
    suspend fun getStoreList(
        @Query("type") type: String = GET_NEARBY_STORE_LIST,
        @Body nearByStoreRequest: NearByStoreRequest
    ): Response<NearByStoreResponse>

}