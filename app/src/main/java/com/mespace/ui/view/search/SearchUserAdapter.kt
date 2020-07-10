package com.mespace.ui.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_search_user_item.view.*
import kotlinx.android.synthetic.main.layout_user_item.view.*

typealias myUser = (Boolean) -> Unit

class SearchUserAdapter(val user: myUser) :
    RecyclerView.Adapter<SearchUserAdapter.CategoryHolder>() {

    val userList = mutableListOf<SearchUserResponse.Detail.Userlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_search_user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<SearchUserResponse.Detail.Userlist>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {


                    userList[position].let { _category ->

                        ivUserPic.loadCircularImage(_category.profile_image)
                        tvUserName.text = _category.name
                        tvUserProfile.text = _category.phone_no
                        tvDes.text = _category.distance

                    }


            }
        }
    }

}