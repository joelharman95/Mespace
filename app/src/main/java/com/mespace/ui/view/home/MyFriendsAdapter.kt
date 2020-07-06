package com.mespace.ui.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_user_item.view.*

typealias myFriend = (Boolean) -> Unit

class MyFriendsAdapter(val myFriend: myFriend) :
    RecyclerView.Adapter<MyFriendsAdapter.CategoryHolder>() {

    val userList = mutableListOf<HomeScreenResponse.Detail.Userlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<HomeScreenResponse.Detail.Userlist>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                if (position <= 7) {
                    if (position == 7) {
                        user_name.text = context.getString(R.string.label_see_more)
                        user_image.loadCircularImage(R.drawable.ic_no_image)
                        setOnClickListener {
                            myFriend.invoke(true)
                        }
                        return
                    }
                    userList[position].let { _category ->
                        user_name.text = _category.name
                        user_image.loadCircularImage(_category.profile_image)
                    }
                    setOnClickListener {
                        myFriend.invoke(false)
                    }
                } else {
                    user_name.visibility = View.GONE
                    user_image.visibility = View.GONE
                }
            }
        }
    }

}