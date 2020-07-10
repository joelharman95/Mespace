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
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.data.network.api.response.SearchStoreUserResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.repository.HomeRepository
import com.mespace.data.repository.ProfileRepository
import com.mespace.data.repository.SearchRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository,
    val context: Context
) : ViewModel() {

    fun getUserList(
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getUserList(onSuccess, onError)
        }
    }


    fun getStoreUselist(
        onSuccess: OnSuccess<SearchStoreUserResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getStoreList(onSuccess, onError)
        }
    }

}