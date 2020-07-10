/*
 * *
 *  * Created by Nethaji on 27/6/20 1:18 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:13 PM
 *
 */

package com.mespace.di

import android.content.Context
import com.mespace.data.network.api.service.HomeApi
import com.mespace.data.network.api.service.ProfileApi
import com.mespace.data.network.api.service.ReferenceApi
import com.mespace.data.network.api.service.SearchUserApi
import com.mespace.data.network.http.HttpClientManager
import com.mespace.data.network.http.createApi
import com.mespace.data.preference.PreferenceManager.Companion.PREFERENCE_NAME
import com.mespace.data.repository.*
import com.mespace.data.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Networking modules here
 * Must be a singleton
 * Also, using the default overload methods
 **/
val NETWORKING_MODULE = module {
    single { HttpClientManager.newInstance(androidContext()) }
    single { get<HttpClientManager>().createApi<ReferenceApi>() }
    single { get<HttpClientManager>().createApi<ProfileApi>() }
    single { get<HttpClientManager>().createApi<HomeApi>() }
    single { get<HttpClientManager>().createApi<SearchUserApi>() }

}

/**
 * Repository modules here
 * Must be a singleton
 **/
val REPOSITORY_MODULE = module {
    single { ReferenceRepository.create(get()) }
    single { ProfileRepository.create(get()) }
    single { HomeRepository.create(get()) }
    single { SearchRepository.create(get()) }
    single { NearestStoreListRepository.create(get()) }
}

/**
 * ViewModel modules here
 **/
val VIEW_MODEL_MODULE = module {
    viewModel { ReferenceViewModel(get(), androidContext()) }
    viewModel { ProfileViewModel(get(), androidContext()) }
    viewModel { HomeViewModel(get(), androidContext()) }
    viewModel { SearchViewModel(get(), androidContext()) }
    viewModel { NearestStoreListViewModel(get(), androidContext()) }

}

fun getSharedPreference(context: Context) =
    context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)