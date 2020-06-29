/*
 * *
 *  * Created by Nethaji on 27/6/20 1:17 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:17 PM
 *
 */

package com.mespace.data.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) : IPreferenceManager {

    val pref: SharedPreferences =
        context.getSharedPreferences("community_app_preference", Context.MODE_PRIVATE)

    override fun setLatitude(Latitude: String?) =
        pref.edit().putString(LATITUDE, Latitude!!).apply()

    override fun getLatitude(): String = pref.getString(LATITUDE, "")!!

    override fun setLongitude(Longitude: String?) =
        pref.edit().putString(LONGITUDE, Longitude!!).apply()

    override fun getLongitude(): String = pref.getString(LONGITUDE, "")!!

    override fun setUserStatus(residenceId: String?) =
        pref.edit().putString(USER_STATUS, residenceId!!).apply()

    override fun getUserStatus(): String = pref.getString(USER_STATUS, "")!!

    override fun setDeviceToken(firebaseToken: String?) {
        pref.edit().putString(TOKEN_ID, firebaseToken!!).apply()
    }

    override fun getDeviceToken(): String = pref.getString(TOKEN_ID, "")!!

    override fun saveToekn(token: String?) =
        pref.edit().putString(TOKEN, token).apply()

    override fun getToekn(): String = pref.getString(TOKEN, "")!!

    override fun sessionId(sessionId: String?) {
        pref.edit().putString(GET_PROFILE_SESSION_ID, sessionId).apply()
    }

    override fun getSessionId(): String = pref.getString(GET_PROFILE_SESSION_ID, "")!!

    override fun logIn(isLogged: Boolean) = pref.edit().putBoolean(LOGGED_IN, isLogged).apply()

    override fun isLoggedIn() = pref.getBoolean(LOGGED_IN, false)

    companion object {
        const val PREFERENCE_NAME = "mespace"
        const val USER_STATUS = "user_status"
        const val TOKEN_ID = "FIREBASE_TOKEN_ID"
        const val TOKEN = "token"
        const val LOGGED_IN = "loggedin"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val GET_PROFILE_SESSION_ID = "getProfileSessionId"
    }
}