package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.ReqAddStore
import com.mespace.data.network.api.request.ReqUpdateStore
import com.mespace.data.network.api.request.ReqViewStore
import com.mespace.data.network.api.response.AddStoreResponse
import com.mespace.data.network.api.response.CategoryResponse
import com.mespace.data.network.api.response.ViewStoreResponse
import com.mespace.data.repository.AddSpaceRepository
import com.mespace.di.*
import kotlinx.coroutines.launch

class EditSpaceViewModel (
    private val repository: AddSpaceRepository,
    val context: Context
) : ViewModel() {
    fun getCategoryList(
        onSuccess: OnSuccess<CategoryResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getCategoryList(onSuccess, onError)
        }
    }

    fun updateSpace(
        reqAddStore: ReqUpdateStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.updateStore(reqAddStore,onSuccess, onError)
        }
    }

    fun viewSpaceDetils(
        reqViewStore: ReqViewStore,
        onSuccess: OnSuccess<ViewStoreResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.viewStore(reqViewStore,onSuccess, onError)
        }
    }
}