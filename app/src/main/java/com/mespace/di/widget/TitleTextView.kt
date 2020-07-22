package com.mespace.di.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.mespace.R

class TitleTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        val typeface = ResourcesCompat.getFont(context, R.font.googlesansregular);
        setTypeface(typeface)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeface = ResourcesCompat.getFont(context, R.font.googlesansregular);
        setTypeface(typeface)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typeface = ResourcesCompat.getFont(context, R.font.googlesansregular);
        setTypeface(typeface)
    }

}