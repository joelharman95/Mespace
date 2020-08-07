package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.AddFavouriteRequest
import com.mespace.data.network.api.request.BusinessDetailsRequest
import com.mespace.data.network.api.request.DeleteFavouriteRequest
import com.mespace.data.network.api.response.AddFavouriteResponse
import com.mespace.data.network.api.response.DeleteFavouriteResponse
import com.mespace.data.network.api.response.StoreDetailsResponse
import com.mespace.data.repository.DetailedRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch


class BusinessDetailsViewmodel(
    private val repository: DetailedRepository,
    val context: Context
) : ViewModel() {

    fun getStoreDetails(
        businessDetailsRequest: BusinessDetailsRequest,
        onSuccess: OnSuccess<StoreDetailsResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getStoreDetails(businessDetailsRequest, onSuccess, onError)
        }
    }


    fun addFavourites(
        addFavouriteRequest: AddFavouriteRequest,
        onSuccess: OnSuccess<AddFavouriteResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.addFavourites(addFavouriteRequest, onSuccess, onError)
        }
    }

    fun removeFavourites(
        deleteFavouriteRequest: DeleteFavouriteRequest,
        onSuccess: OnSuccess<DeleteFavouriteResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.deleteFavourites(deleteFavouriteRequest, onSuccess, onError)
        }
    }
}