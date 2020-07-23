package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.ReqIsHomePageExists
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.request.ReqUpdateUser
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

    fun getHomePageList(
            reqUpdateUser: ReqIsHomePageExists,
            onSuccess: OnSuccess<HomeScreenResponse>,
            onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getHomePageList(
                    reqUpdateUser,onSuccess, onError)
        }
    }

}
