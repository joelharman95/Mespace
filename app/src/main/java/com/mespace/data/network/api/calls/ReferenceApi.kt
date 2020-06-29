/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:41 PM
 *
 */

package com.mespace.data.network.api.calls

import com.mespace.data.network.api.request.ReferenceRequest
import com.mespace.data.network.api.response.ReferenceResponse
import com.mespace.di.utility.Extras.AUTHORIZATION
import com.mespace.di.utility.Extras.HEADER_LANGUAGE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReferenceApi {

    @POST("URL_REQUEST")
    suspend fun getToken(
        @Header(AUTHORIZATION) auth: String?= null,
        @Header(HEADER_LANGUAGE) language: String,
        @Body body: ReferenceRequest
    ): Response<ReferenceResponse>

}