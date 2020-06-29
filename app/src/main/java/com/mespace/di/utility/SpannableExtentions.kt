/*
 * *
 *  * Created by Nethaji on 28/6/20 10:03 AM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 28/6/20 10:03 AM
 *
 */

package com.mespace.di.utility

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import com.mespace.R
import com.mespace.di.getCompatColor
import com.mespace.di.getCompatSize

fun TextView.applySpanPo(stringPrefix: String, stringSuffix: String, color: Int) {
    text = buildSpannedString {
        inSpans {
            append(stringPrefix)
        }.inSpans(
            ForegroundColorSpan(getCompatColor(color))
        ) {
            bold { append(" $stringSuffix") }
        }
    }
}

fun TextView.applySpanPo(
    stringPrefix: String,
    string: String,
    stringSuffix: String,
    font: Int
) {
    text = buildSpannedString {
        inSpans(
            ForegroundColorSpan(getCompatColor(R.color.black))
        ) {
            append(stringPrefix)
        }.inSpans(
            append(string),
            /*context.getCompatTypefaceSpan(R.font.poppins_regular),*/
            AbsoluteSizeSpan(getCompatSize(R.dimen.size_12))
        ) {

        }.inSpans(
            ForegroundColorSpan(getCompatColor(R.color.black))
        ) {
            bold { append(stringSuffix) }
        }
    }
}

fun TextView.applyBoldSpan(stringPrefix: String, stringSuffix: String, font: Int) {
    text = buildSpannedString {
        inSpans(
            ForegroundColorSpan(getCompatColor(R.color.black))
        ) {
            append(stringPrefix)
        }.inSpans {
            append(stringSuffix)
        }
    }
}