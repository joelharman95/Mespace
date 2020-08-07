package com.mespace.ui.view.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.SearchStoreUserResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_search_user_item.view.*
import kotlinx.android.synthetic.main.layout_search_user_item.view.ivUserPic

import kotlinx.android.synthetic.main.layout_search_user_item.view.tvUserName


typealias storeUser = (Boolean) -> Unit

class SearchStoreUserAdapter(val user: storeUser) :
    RecyclerView.Adapter<SearchStoreUserAdapter.CategoryHolder>() {

    val userList = mutableListOf<SearchStoreUserResponse.Detail.Storelist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_search_store_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<SearchStoreUserResponse.Detail.Storelist>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {


                userList[position].let { _category ->

                    ivUserPic.loadCircularImage(_category.profile_image)
                    tvUserName.text = _category.name
                    tvDis.text = _category.distance
                   /* tvStoreTag.text=_category.keywords
                    tvStoreCategory.text=_category.description
                    tvStoreDescription.text=_category.description

                    tvDes.text = _category.name
                    tvShopStatus.text = _category.open_hours
                    tvCategory.text = _category.close_hours*/

                }


            }
        }
    }

}