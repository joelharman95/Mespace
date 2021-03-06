/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:41 PM
 *
 */

package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.MySpaceRequest
import com.mespace.data.network.api.request.ProfileSettingRequest
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.request.ReqUpdateUser
import com.mespace.data.network.api.response.MySpaceResponse
import com.mespace.data.network.api.response.ProfileSettingResponse
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.data.network.api.response.ResUserUpdate
import com.mespace.di.utility.API.INDEX
import com.mespace.di.utility.API.MY_SPACEE
import com.mespace.di.utility.API.PROFILE_SETTING
import com.mespace.di.utility.API.UPDATE_PROFILE
import com.mespace.di.utility.API.USER_EXISTS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileApi {

    @POST(INDEX)
    suspend fun isUserExists(
        @Query("type") type: String = USER_EXISTS,
        @Body reqIsUserExists: ReqIsUserExists
    ): Response<ResIsUserExists>

    @POST(INDEX)
    suspend fun getProfileSettings(
        @Query("type") type: String = PROFILE_SETTING,
        @Body userId: ProfileSettingRequest
    ):Response<ProfileSettingResponse>

    @POST(INDEX)
    suspend fun getMySpaceList(
        @Query("type") type:String=MY_SPACEE,
    @Body mySpaceRequest: MySpaceRequest
    ):Response<MySpaceResponse>

    @POST(INDEX)
    suspend fun addOrUpdateProfile(
        @Query("type") type: String = UPDATE_PROFILE,
        @Body reqUpdateUser: ReqUpdateUser
    ): Response<ResUserUpdate>

}