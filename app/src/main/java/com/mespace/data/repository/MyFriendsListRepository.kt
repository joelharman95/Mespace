package com.mespace.data.repository

import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.data.network.api.service.MyFriendsListApi
import com.mespace.di.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface MyFriendsListRepository {

    suspend fun getUserList(
        searchUserRequest: MyUserRequest,
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    )

    companion object Factory {

        fun create(api: MyFriendsListApi): MyFriendsListRepository =
            MyFriendsListRepositoryImpl(api)
    }

}

private class MyFriendsListRepositoryImpl(private val userApi: MyFriendsListApi) :
    MyFriendsListRepository {
    override suspend fun getUserList(
        myUserRequest: MyUserRequest,
        onSuccess: OnSuccess<SearchUserResponse>,
        onError: OnError<String>
    ) {


        withContext(Dispatchers.IO) {
            try {
                val response = userApi.getUserList(myUserRequest = myUserRequest)
                if (response.isSuccessful) {



                    response.body()?.let {
                        if (it.status.toString().isSuccess())
                            withContext(Dispatchers.Main) {
                                onSuccess(it)
                            }
                        else
                            withContext(Dispatchers.Main) {
                                onError(it.message)
                            }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError(response.message().toString())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {}
            }
        }


    }


}
