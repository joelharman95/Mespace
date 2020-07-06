package com.mespace.ui.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_user_item.view.*

typealias category = (HomeScreenResponse.Detail.Userlist) -> Unit

class MyFriendsAdapter(val category: category) :
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
                if (position == 7 && itemCount < 7) {
                    user_name.text = "See More"
                    user_image.loadCircularImage(R.drawable.ic_no_image)
                    return
                }
                userList[position].let { _category ->
                    println("GET______" + _category.name)
                    user_name.text = _category.name
                    user_image.loadCircularImage(_category.profile_image)

                }
            }
        }
    }

}