/*
 * *
 *  * Created by Nethaji on 27/6/20 1:42 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:42 PM
 *
 */

package com.mespace.data.network.api.request

import com.google.gson.annotations.SerializedName

class ReferenceRequest(
    @SerializedName("request_string") val requestString: String? = null
)