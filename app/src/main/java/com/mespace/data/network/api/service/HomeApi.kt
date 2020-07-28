package com.mespace.data.network.api.service

import com.mespace.data.network.api.request.ReqIsHomePageExists
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.di.utility.API.HOME_API_Static
import com.mespace.di.utility.API.Home_API
import com.mespace.di.utility.API.INDEX
import com.mespace.di.utility.API.USER_EXISTS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApi {

    @POST(INDEX)
    suspend fun getHomePageList(
            @Query("type") type: String = HOME_API_Static,
            @Body reqIsHomePageExists: ReqIsHomePageExists
    ): Response<HomeScreenResponse>

}
