package com.mespace.ui.view.closesttoyou

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.ClosestToResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_closest_user_item.view.*
import kotlinx.android.synthetic.main.layout_closest_user_item.view.border
import kotlinx.android.synthetic.main.layout_closest_user_item.view.textLayout
import kotlinx.android.synthetic.main.layout_near_store_item_list.view.*
import java.util.*


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

                    if(_category.profile_image.isEmpty() || _category.profile_image.toString().contains("no_image")){
                        drawableColorChange(textLayout)
                        border.text=_category.name.first().toString()
                    }else{
                        ivUserPic.loadCircularImage(_category.profile_image)
                        textLayout.visibility=View.GONE
                    }
                    tvUserName.text = _category.name
                    if(_category.tag_name==null){
                        tvTag.visibility=View.GONE
                    }
                    if(_category.tag_name.isNotEmpty()){
                        tvTag.text = _category.tag_name
                    }else{
                        tvTag.visibility=View.GONE
                    }

                    tvDistance.text = _category.distance

                }


            }
        }
    }

    fun drawableColorChange(view: RelativeLayout){
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