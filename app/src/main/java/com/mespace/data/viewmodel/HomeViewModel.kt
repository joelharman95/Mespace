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
import com.mespace.data.repository.HomeRepository
import com.mespace.data.repository.ProfileRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    val context: Context
) : ViewModel() {

    fun getUserList(
        onSuccess: OnSuccess<HomeScreenResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getUserList(onSuccess, onError)
        }
    }

}