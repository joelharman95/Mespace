package com.mespace.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mespace.data.network.api.request.ProfileSettingRequest
import com.mespace.data.network.api.request.ReqIsUserExists
import com.mespace.data.network.api.response.ProfileSettingResponse
import com.mespace.data.network.api.response.ResIsUserExists
import com.mespace.data.repository.ProfileRepository
import com.mespace.di.*
import kotlinx.coroutines.launch

class ProfileSettingViewModel (
    private val repository: ProfileRepository,
    val context: Context
) : ViewModel() {


    fun getProfileSettings(
        profileSettingRequest: ProfileSettingRequest,
        onSuccess: OnSuccess<ProfileSettingResponse>,
        onError: OnError<String>
    ) {
        viewModelScope.launch {
            repository.getProfileSettings(profileSettingRequest, onSuccess, onError)
        }
    }

}