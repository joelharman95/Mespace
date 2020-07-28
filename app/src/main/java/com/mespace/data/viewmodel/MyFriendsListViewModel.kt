package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.repository.MyFriendsListRepository
import com.mespace.di.OnError
import com.mespace.di.OnSuccess
import kotlinx.coroutines.launch

class MyFriendsListViewModel(
    private val repository: MyFriendsListRepository,
    val context: Context
) : ViewModel() {

    fun getMyFriendsList(
        searchUserRequest: MyUserRequest,
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getUserList(searchUserRequest, onSuccess, onError)
        }
    }
}