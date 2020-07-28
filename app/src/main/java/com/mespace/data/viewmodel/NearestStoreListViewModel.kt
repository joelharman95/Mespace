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
import com.mespace.data.network.api.request.NearByStoreRequest
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.*
import com.mespace.data.repository.HomeRepository
import com.mespace.data.repository.NearestStoreListRepository
import com.mespace.data.repository.ProfileRepository
import com.mespace.data.repository.SearchRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class NearestStoreListViewModel(
    private val repository: NearestStoreListRepository,
    val context: Context
) : ViewModel() {




    fun getStoreUselist(
        nearestStoreRequest: NearByStoreRequest,
        onSuccess: OnSuccess<NearByStoreResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getUserList(nearestStoreRequest,onSuccess, onError)
        }
    }

}