package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.ClosestToRequest
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.ClosestToResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.di.utility.API
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ClosestToApi {

    @POST(API.INDEX)
    suspend fun getClosestTo(
        @Query("type") type: String = API.CLOSEST_TO_API,
        @Body closestToRequest: ClosestToRequest
    ): Response<ClosestToResponse>


}