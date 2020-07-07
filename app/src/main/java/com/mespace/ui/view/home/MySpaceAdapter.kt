package com.mespace.ui.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_user_item.view.*

typealias mySpace = (Boolean) -> Unit

class MySpaceAdapter(val mySpace: mySpace) :
    RecyclerView.Adapter<MySpaceAdapter.CategoryHolder>() {

    val userList = mutableListOf<HomeScreenResponse.Detail.Mespace>()

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

    fun addCategoryList(_categoryList: List<HomeScreenResponse.Detail.Mespace>) {
        userList.addAll(_categoryList)
        userList.add(HomeScreenResponse.Detail.Mespace())
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                if (position == itemCount - 1) {
                    user_name.text = "Add a space"
                    user_image.loadCircularImage(R.drawable.ic_no_image)
                    setOnClickListener {
                        mySpace.invoke(true)
                    }
                    return
                }
                userList[position].let { _category ->
                    user_name.text = _category.name
                    user_image.loadCircularImage(_category.profile_image.toString())
                    setOnClickListener {
                        mySpace.invoke(false)
                    }
                }
            }
        }
    }
}