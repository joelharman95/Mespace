package com.mespace.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
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

        ivProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        ivNotification.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }

    }

}
