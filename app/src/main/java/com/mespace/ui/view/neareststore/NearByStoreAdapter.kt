package com.mespace.ui.view.neareststore

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.NearByStoreResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_near_store_item_list.view.*


typealias storeUser = (Boolean) -> Unit

class NearByStoreAdapter(val user: storeUser) :
    RecyclerView.Adapter<NearByStoreAdapter.CategoryHolder>() {

    val userList = mutableListOf<NearByStoreResponse.Detail.Store_list>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_near_store_item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<NearByStoreResponse.Detail.Store_list>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindUi(position: Int) {
            view.apply {


                userList[position].let { _category ->

                    ivStorePic.loadCircularImage(_category.profile_image)
                    tvStoreName.text = _category.name
                    tvStoreDistance.text = _category.distance
                    tvStoreTag.text=_category.tag_name
                    tvStoreCategory.text=_category.categories

                    tvStoreDescription.text=_category.description
                    if(_category.open_close!="1"){
                        tvStoreStatus.text=context.getString(R.string.closed)
                    }else{
                        tvStoreStatus.text="Open till "+_category.close_hours
                    }

                }


            }
        }
    }

}