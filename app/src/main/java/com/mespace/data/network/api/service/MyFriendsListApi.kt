package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.di.utility.API.Favourites_List
import com.mespace.di.utility.API.INDEX

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MyFriendsListApi {

    @POST(INDEX)
    suspend fun getUserList(
        @Query("type") type: String = Favourites_List,
        @Body myUserRequest: MyUserRequest
    ): Response<SearchUserResponse>


}