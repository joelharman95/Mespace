/*
 * *
 *  * Created by Nethaji on 27/6/20 1:32 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:32 PM
 *
 */

package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.ReqAddStore
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.*
import com.mespace.data.repository.AddSpaceRepository
import com.mespace.data.repository.HomeRepository
import com.mespace.data.repository.ProfileRepository
import com.mespace.data.repository.SearchRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class AddSpaceViewModel(
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


   fun addNewStore(
           reqAddStore: ReqAddStore,
        onSuccess: OnSuccess<AddStoreResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.addNewStore(reqAddStore,onSuccess, onError)
        }
    }

}