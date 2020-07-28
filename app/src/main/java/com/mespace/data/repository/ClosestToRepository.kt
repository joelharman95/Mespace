package com.mespace.data.repository

import com.mespace.data.network.api.request.ClosestToRequest
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.ClosestToResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.network.api.service.ClosestToApi
import com.mespace.data.network.api.service.MyFriendsListApi
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ClosestToRepository {

    suspend fun getClosest(
        closestToRequest: ClosestToRequest,
        onSuccess: OnSuccess<ClosestToResponse>,
        onError: OnError<String>
    )

    companion object Factory {

        fun create(api: ClosestToApi): ClosestToRepository =
            ClosestToRepositoryImpl(api)
    }

}

class ClosestToRepositoryImpl(private val api: ClosestToApi) : ClosestToRepository {
    override suspend fun getClosest(
        closestToRequest: ClosestToRequest,
        onSuccess: OnSuccess<ClosestToResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = api.getClosestTo(closestToRequest=closestToRequest)
                if (response.isSuccessful) {

                    response.body()?.let {
                        if (it.status.toString().isSuccess())
                            withContext(Dispatchers.Main) {
                                onSuccess(it)
                            }
                        else
                            withContext(Dispatchers.Main) {
                                onError(it.message)
                            }
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
