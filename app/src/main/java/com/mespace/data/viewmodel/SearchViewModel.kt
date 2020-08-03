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
import com.mespace.data.network.api.request.RequSearchStoreUser
import com.mespace.data.network.api.request.RequSearchUser
import com.mespace.data.network.api.response.*
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
           requSearchUser: RequSearchUser,
        onSuccess: OnSuccess<UserSearchResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getUserList(requSearchUser,onSuccess, onError)
        }
    }


    fun getStoreUserListItem(requSearchStoreUser: RequSearchStoreUser, onSuccess: OnSuccess<StoreUserSearchResponse>, onError: OnError<String>
    )
    {
        viewModelScope.launch {
            repository.getSearchStoreListUser(requSearchStoreUser,onSuccess,onError)
        }
    }



}