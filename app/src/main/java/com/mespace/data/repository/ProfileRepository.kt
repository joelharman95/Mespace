/*
 * *
 *  * Created by Nethaji on 27/6/20 1:32 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:32 PM
 *
 */

package com.mespace.data.repository

import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.data.network.api.service.ProfileApi
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun isUserExists(
        reqIsUserExists: ReqIsUserExists,
        onSuccess: OnSuccess<ResIsUserExists>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.isUserExists(reqIsUserExists = reqIsUserExists)
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status.toString().isSuccess())
                            withContext(Dispatchers.Main) { onSuccess(it) }
                        else
                            withContext(Dispatchers.Main) { onError(it.message.toString()) }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(response.message().toString())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {}
            }
        }
    }

}

interface ProfileRepository {

    suspend fun isUserExists(
        reqIsUserExists: ReqIsUserExists,
        onSuccess: OnSuccess<ResIsUserExists>,
        onError: OnError<String>
    )

    companion object Factory {
        fun create(api: ProfileApi): ProfileRepository =
            ProfileRepositoryImpl(api)
    }

}