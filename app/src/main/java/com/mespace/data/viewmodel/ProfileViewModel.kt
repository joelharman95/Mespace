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
import com.mespace.data.network.api.request.ReqUpdateUser
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.data.network.api.response.ResUserUpdate
import com.mespace.data.repository.ProfileRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
    val context: Context
) : ViewModel() {

    //  fun getToken(block: (response: TokenResponse) -> Unit) {  // Higher order function can also be used
    fun isUserExists(
        reqIsUserExists: ReqIsUserExists,
        onSuccess: OnSuccess<ResIsUserExists>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.isUserExists(reqIsUserExists, onSuccess, onError)
        }
    }

    fun addOrUpdateProfile(
        reqUpdateUser: ReqUpdateUser,
        onSuccess: OnSuccess<ResUserUpdate>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.addOrUpdateProfile(reqUpdateUser, onSuccess, onError)
        }
    }

}