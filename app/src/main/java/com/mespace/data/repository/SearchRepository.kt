/*
 * *
 *  * Created by Nethaji on 27/6/20 1:32 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:32 PM
 *
 */

package com.mespace.data.repository

import com.mespace.data.network.api.response.SearchStoreUserResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.network.api.service.SearchUserApi
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class SearchRepositoryImpl(
    private val api: SearchUserApi) : SearchRepository {

    override suspend fun getUserList(
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.getUserList()
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

    override suspend fun getStoreList(
        onSuccess: OnSuccess<SearchStoreUserResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.getStoreList()
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



interface SearchRepository {


    suspend fun getUserList(
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    )

    suspend fun getStoreList(
        onSuccess: OnSuccess<SearchStoreUserResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun create(api: SearchUserApi): SearchRepository =
            SearchRepositoryImpl(api)
    }

}