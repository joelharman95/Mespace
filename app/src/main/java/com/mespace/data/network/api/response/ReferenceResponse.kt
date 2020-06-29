/*
 * *
 *  * Created by Nethaji on 27/6/20 1:43 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 1:42 PM
 *
 */

package com.mespace.data.network.api.response

import com.google.gson.annotations.SerializedName

class ReferenceResponse(
    val status: String? = null,
    @SerializedName("response_string") val responseString: String? = null
)