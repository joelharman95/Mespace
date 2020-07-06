package com.mespace.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.mespace.R
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_image.loadCircularImage("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
getUserdetails()
    }

    private fun getUserdetails() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
