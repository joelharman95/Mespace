package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.ClosestToRequest
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.ClosestToResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.repository.ClosestToRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class ClosestToViewModel(
    private val repository: ClosestToRepository,
    val context: Context
) : ViewModel() {

    fun getClosestTo(
        closestToRequest: ClosestToRequest,
        onSuccess: OnSuccess<ClosestToResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getClosest(closestToRequest, onSuccess, onError)
        }
    }

}