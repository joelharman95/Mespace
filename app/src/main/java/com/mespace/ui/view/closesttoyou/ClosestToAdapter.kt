package com.mespace.ui.view.closesttoyou

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.ClosestToResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_closest_user_item.view.*


typealias myFriends = (Boolean) -> Unit

class ClosestToAdapter(val user: myFriends) :
    RecyclerView.Adapter<ClosestToAdapter.CategoryHolder>() {

    val userList = mutableListOf<ClosestToResponse.Detail.Users_list>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_closest_user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<ClosestToResponse.Detail.Users_list>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {


                userList[position].let { _category ->

                    ivUserPic.loadCircularImage(_category.profile_image)
                    tvUserName.text = _category.name
                    if(_category.tag_name==null){
                        tvTag.visibility=View.GONE
                    }
                    tvTag.text = _category.tag_name
                    tvDistance.text = _category.distance

                }


            }
        }
    }

}