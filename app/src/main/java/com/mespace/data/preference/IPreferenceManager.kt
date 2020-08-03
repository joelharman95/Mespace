/*
 * *
 *  * Created by Nethaji on 27/6/20 1:17 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 12:33 PM
 *
 */

package com.mespace.data.preference

interface IPreferenceManager {

    fun saveIsLaunchedOnce(isLaunched: Boolean)
    fun getIsLaunchedOnce(): Boolean

    fun saveToken(token: String?)
    fun getToken(): String

    fun sessionId(sessionId: String?)
    fun getSessionId(): String

    fun logIn(isLogged: Boolean)
    fun isLoggedIn(): Boolean

    fun setUserId(userId: String?)
    fun getUserId(): String

    fun setUserName(userName: String?)
    fun getUserName(): String

    fun setUserProfile(userProfile: String?)
    fun getUserProfile(): String

    fun setDeviceToken(firebaseToken: String?)
    fun getDeviceToken(): String

    fun setLatitude(Latitude: String?)
    fun getLatitude(): String

    fun setLongitude(Longitude: String?)
    fun getLongitude(): String

    fun setTitle(title:String)
    fun getTitle():String

    fun setUrl(url:String)
    fun getUrl():String

}