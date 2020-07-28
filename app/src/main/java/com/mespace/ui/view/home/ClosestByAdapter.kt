package com.mespace.ui.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_store_item.view.*
import kotlinx.android.synthetic.main.layout_user_item.view.user_image
import kotlinx.android.synthetic.main.layout_user_item.view.user_name

typealias closesBy = (Boolean) -> Unit

class ClosestByAdapter(val closesBy: closesBy) :
    RecyclerView.Adapter<ClosestByAdapter.CategoryHolder>() {

    val closerList = mutableListOf<HomeScreenResponse.Detail.ClosestUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_store_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = closerList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<HomeScreenResponse.Detail.ClosestUser>) {
        closerList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                if (position < 6) {
                    closerList[position].let { _category ->
                        user_name.text = _category.name
                        user_image.loadCircularImage(_category.profile_image)
                        user_distance.text = _category.distance
                        setOnClickListener {
                            closesBy.invoke(false)
                        }
                    }
                } else {
                    if (position == 6) {
                        user_name.text = "Show more"
                        user_distance.visibility = View.GONE
                        user_image.loadCircularImage(R.drawable.ic_icon_show_more)
                        setOnClickListener {
                            closesBy.invoke(true)
                        }
                        return
                    } else {
                        user_image.visibility = View.GONE
                        user_name.visibility = View.GONE
                        user_distance.visibility = View.GONE
                    }
                }
            }
        }
    }

}