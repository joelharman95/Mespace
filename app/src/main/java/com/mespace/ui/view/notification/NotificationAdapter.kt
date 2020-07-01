package com.mespace.ui.view.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.ResNotification

typealias notifiction = (ResNotification) -> Unit

class NotificationAdapter(val notifiction: notifiction) :
    RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    var title = ""
    val notificationList = mutableListOf<ResNotification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        return NotificationHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_notification_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = notificationList.size

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addNotificationList(_notificationList: List<ResNotification>) {
        notificationList.addAll(_notificationList)
        notifyDataSetChanged()
    }

    inner class NotificationHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                notificationList[position].let { _notification ->

                    if (title == _notification.title) {     //  TODO ::  ::  need to do like a header title

                    } else {
                        title = _notification.title.toString()
                    }

                    setOnClickListener {
                        notifiction.invoke(_notification)
                    }
                }
            }
        }
    }

}