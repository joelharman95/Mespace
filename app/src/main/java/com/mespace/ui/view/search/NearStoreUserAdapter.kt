package com.mespace.ui.view.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.NearByStoreResponse
import com.mespace.data.network.api.response.StoreUserSearchResponse
import com.mespace.di.loadCircularImage
import com.mespace.utils.CommonTextView
import kotlinx.android.synthetic.main.layout_near_store_item_list.view.*
import java.util.*


typealias NearstoreUser = (Boolean) -> Unit

class NearStoreUserAdapter(val user: NearstoreUser) :
    RecyclerView.Adapter<NearStoreUserAdapter.CategoryHolder>() {

    val userList = mutableListOf<StoreUserSearchResponse.Detail.Users>()

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

    fun addCategoryList(_categoryList: List<StoreUserSearchResponse.Detail.Users>) {
        userList.clear()
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }



    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindUi(position: Int) {
            view.apply {

                userList[position].let { _category ->

                    if(_category.profile_image.isEmpty() || _category.profile_image.toString().contains("no_image")){
                        drawableColorChange(textLayout)
                        border.text=_category.name.first().toString()
                    }else{
                        ivStorePic.loadCircularImage(_category.profile_image)
                        textLayout.visibility=View.GONE
                    }

                    tvStoreName.text = _category.name
                    tvStoreDistance.text = _category.distance
                    tvStoreTag.text=_category.keywords
                    tvStoreCategory.text=_category.description
                    tvStoreDescription.text=_category.description
                    if(_category.open_hours!="1"){
                        tvStoreStatus.text=context.getString(R.string.closed)
                    }else{
                        tvStoreStatus.text="Open till "+_category.close_hours
                    }

                    /*cvCategory.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                    cvCategory.strokeColor = ContextCompat.getColor(context, R.color.black)
                    cvCategory.strokeWidth=1
                    cvCategory.radius=50f
                    cvCategory.invalidate()*/
                }


            }
        }
    }

    fun drawableColorChange(view:RelativeLayout){
        val rnd = Random()
        val color: Int =
            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        view.setBackgroundColor(color)
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.cornerRadius = 100f
       // gd.setStroke(6, Color.BLACK)
        view.background=gd
    }

}