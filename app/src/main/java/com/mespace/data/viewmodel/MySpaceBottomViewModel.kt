package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.MySpaceRequest
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.MySpaceResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.repository.ProfileRepository
import com.mespace.di.*
import kotlinx.coroutines.launch

class MySpaceBottomViewModel(
    private val repository: ProfileRepository,
    val context: Context
) : ViewModel() {

    fun getMySpaceList(
        mySpaceRequest: MySpaceRequest,
        onSuccess: OnSuccess<MySpaceResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getMySpaceList(mySpaceRequest,onSuccess,onError)
        }
    }

}