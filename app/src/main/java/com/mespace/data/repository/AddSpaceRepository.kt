/*
 * *
 *  * Created by Nethaji on 27/6/20 1:32 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:32 PM
 *
 */

package com.mespace.data.repository

import com.mespace.data.network.api.request.ReqAddStore
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.request.ReqUpdateStore
import com.mespace.data.network.api.request.ReqViewStore
import com.mespace.data.network.api.response.*
import com.mespace.data.network.api.service.AddSpaceApi
import com.mespace.data.network.api.service.SearchUserApi
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class AddSpaceRepositoryImpl(
    private val api: AddSpaceApi) : AddSpaceRepository {

    override suspend fun getCategoryList(
        onSuccess: OnSuccess<CategoryResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.getCategoryList()
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

    override suspend fun addNewStore(
            reqAddStore: ReqAddStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.getAddNewStore(reqAddStore = reqAddStore)
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

    override suspend fun updateStore(
        reqUpdateStore: ReqUpdateStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {

                val response = api.updateStore(reqUpdateStore=reqUpdateStore)
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

    override suspend fun viewStore(
        reqViewStore: ReqViewStore,
        onSuccess: OnSuccess<ViewStoreResponse>,
        onError: OnError<String>
    ) {

        withContext(Dispatchers.IO) {
            try {

                val response = api.viewStore(reqViewStore=reqViewStore)
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



interface AddSpaceRepository {


    suspend fun getCategoryList(
        onSuccess: OnSuccess<CategoryResponse>,
        onError: OnError<String>
    )

    suspend fun addNewStore(
            reqAddStore: ReqAddStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    )

    suspend fun updateStore(
        reqAddStore: ReqUpdateStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    )

    suspend fun viewStore(
        reqViewStore: ReqViewStore,
        onSuccess: OnSuccess<ViewStoreResponse>,
        onError: OnError<String>
    )

    companion object Factory {
        fun create(api: AddSpaceApi): AddSpaceRepository =
                AddSpaceRepositoryImpl(api)
    }

}