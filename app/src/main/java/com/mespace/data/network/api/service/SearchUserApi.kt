/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:41 PM
 *
 */

package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.RequSearchStoreUser
import com.mespace.data.network.api.request.RequSearchUser
import com.mespace.data.network.api.response.SearchStoreUserResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.network.api.response.StoreUserSearchResponse
import com.mespace.data.network.api.response.UserSearchResponse
import com.mespace.di.utility.API.INDEX
import com.mespace.di.utility.API.Search_Store_Api
import com.mespace.di.utility.API.Search_User_Api
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchUserApi {

    @POST(INDEX)
    suspend fun getUserList(

        @Query("type") type: String = Search_User_Api,
        @Body requSearchUser: RequSearchUser
    ): Response<UserSearchResponse>

    @POST(INDEX)
    suspend fun getStoreListNew(

        @Query("type") type: String = Search_Store_Api,
        @Body requSearchStoreUser: RequSearchStoreUser
    ): Response<StoreUserSearchResponse>

}