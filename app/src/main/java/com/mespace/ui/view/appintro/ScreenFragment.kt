/*
 * *
 *  * Created by Nethaji on 27/6/20 5:15 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 27/6/20 5:15 PM
 *
 */

package com.mespace.ui.view.appintro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mespace.R
import kotlinx.android.synthetic.main.fragment_screen.*

class ScreenFragment(val position: Int, val title: String, val subTitle: String, val pic: Int) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = title
        tvSubTitle.text = subTitle
        ivPic.setImageResource(pic)
        if (position == 1) {
            view1.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_shape_blue)
            view2.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_shape_grey)
            /*  view1.background = resources.getDrawable(R.drawable.circle_shape_blue)
              view2.background = resources.getDrawable(R.drawable.circle_shape_grey)*/
        } else {
            view1.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_shape_grey)
            view2.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.circle_shape_blue)
            /*view1.background = resources.getDrawable(R.drawable.circle_shape_grey)
            view2.background = resources.getDrawable(R.drawable.circle_shape_blue)*/
        }
    }

    companion object {
        fun newInstance(position: Int, title: String, subTitle: String, pic: Int) =
            ScreenFragment(position, title, subTitle, pic)
    }

}
