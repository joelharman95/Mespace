package com.mespace.ui.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : Fragment(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivNotificationBack.setOnClickListener {
            findNavController().navigateUp()
        }

        rvNotification.adapter = NotificationAdapter {

        }

        getNotificationList()

    }

    private fun getNotificationList() {
        //  (rvNotification.adapter as NotificationAdapter).addNotificationList()
    }

}
