package com.mespace.data.repository

import com.mespace.data.network.api.request.AddFavouriteRequest
import com.mespace.data.network.api.request.BusinessDetailsRequest
import com.mespace.data.network.api.request.DeleteFavouriteRequest
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.AddFavouriteResponse
import com.mespace.data.network.api.response.DeleteFavouriteResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.network.api.response.StoreDetailsResponse
import com.mespace.data.network.api.service.MyFriendsListApi
import com.mespace.data.network.api.service.NearestStoreList
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import com.mespace.di.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DetailedRepository {

    suspend fun getStoreDetails(
        businessDetailsRequest: BusinessDetailsRequest,
        onSuccess: OnSuccess<StoreDetailsResponse>,
        onError: OnError<String>
    )

    suspend fun addFavourites(
        addFavouriteRequest: AddFavouriteRequest,
        onSuccess: OnSuccess<AddFavouriteResponse>,
        onError: OnError<String>
    )

    suspend fun deleteFavourites(
        deleteFavouriteRequest: DeleteFavouriteRequest,
    onSuccess: OnSuccess<DeleteFavouriteResponse>,
        onError: OnError<String>
    )

    companion object Factory {

        fun create(api: NearestStoreList): DetailedRepository =
            DetailedRepositoryImpl(api)
    }

}

private class DetailedRepositoryImpl(private val userApi: NearestStoreList) :
    DetailedRepository {
    override suspend fun getStoreDetails(
        businessDetailsRequest: BusinessDetailsRequest,
        onSuccess: OnSuccess<StoreDetailsResponse>,
        onError: OnError<String>
    ) {


        withContext(Dispatchers.IO) {
            try {
                val response = userApi.getStoreDetails(businessDetailsRequest = businessDetailsRequest)
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

    override suspend fun addFavourites(
        addFavouriteRequest: AddFavouriteRequest,
        onSuccess: OnSuccess<AddFavouriteResponse>,
        onError: OnError<String>
    ) {
        withContext(Dispatchers.IO) {
            try {
                val response = userApi.addFavourite(addFavouriteRequest = addFavouriteRequest)
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

    override suspend fun deleteFavourites(
        deleteFavouriteRequest: DeleteFavouriteRequest,
        onSuccess: OnSuccess<DeleteFavouriteResponse>,
        onError: OnError<String>
    ) {

        withContext(Dispatchers.IO) {
            try {
                val response = userApi.deleteFavourites(deleteFavouriteRequest = deleteFavouriteRequest)
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