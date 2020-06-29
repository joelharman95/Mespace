/*
 * *
 *  * Created by Nethaji on 27/6/20 1:32 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:32 PM
 *
 */

package com.mespace.data.repository

import com.mespace.data.network.api.calls.ReferenceApi
import com.mespace.data.network.api.request.ReferenceRequest
import com.mespace.data.network.api.response.ReferenceResponse
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class ReferenceRepositoryImpl(
    private val api: ReferenceApi
) : ReferenceRepository {

    override suspend fun getToken(
        referenceBody: ReferenceRequest,
        onSuccess: OnSuccess<ReferenceResponse>,
        onError: OnError<Any>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.getToken(body = referenceBody, language = "lan")
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status.toString().isSuccess())
                            withContext(Dispatchers.Main) { onSuccess(it) }
                        else
                            withContext(Dispatchers.Main) { onError("No Data Available") }
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

interface ReferenceRepository {

    suspend fun getToken(
        referenceBody: ReferenceRequest,
        onSuccess: OnSuccess<ReferenceResponse>,
        onError: OnError<Any>
    )

    companion object Factory {
        fun create(api: ReferenceApi): ReferenceRepository =
            ReferenceRepositoryImpl(api)
    }

}