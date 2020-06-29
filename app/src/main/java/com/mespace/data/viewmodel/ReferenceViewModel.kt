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
import com.mespace.data.network.api.request.ReferenceRequest
import com.mespace.data.network.api.response.ReferenceResponse
import com.mespace.data.repository.ReferenceRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class ReferenceViewModel(
    private val repository: ReferenceRepository,
    val context: Context
) : ViewModel() {

    //  fun getToken(block: (response: TokenResponse) -> Unit) {  // Higher order function can also be used
    fun getToken(
        referenceBody: ReferenceRequest,
        onSuccess: OnSuccess<ReferenceResponse>,
        onError: OnError<Any>
    ) {
        viewModelScope.launch {
            repository.getToken(referenceBody, onSuccess, onError)
        }
    }

}