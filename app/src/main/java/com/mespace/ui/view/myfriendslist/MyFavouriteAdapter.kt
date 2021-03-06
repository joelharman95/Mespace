package com.mespace.ui.view.myfriendslist

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.SearchUserResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_friends_list.view.*
import kotlinx.android.synthetic.main.layout_friends_list.view.border
import kotlinx.android.synthetic.main.layout_friends_list.view.cvCategory
import kotlinx.android.synthetic.main.layout_friends_list.view.textLayout
import kotlinx.android.synthetic.main.layout_near_store_item_list.view.*
import java.util.*

typealias myFriends = (Boolean) -> Unit

class MyFavouriteAdapter(val user: myFriends) :
    RecyclerView.Adapter<MyFavouriteAdapter.CategoryHolder>() {

    val userList = mutableListOf<SearchUserResponse.Detail.Userlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_friends_list,
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
        @SuppressLint("SetTextI18n")
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
                    tvTag.text = _category.tag_name
                    /*if(_category.distance!=null){
                        tvDistance.text = _category.distance
                    }*/

                    when (_category.type) {
                        "S" -> {
                            if (_category.open_close != "1") {
                                tvShopStatus.visibility = View.VISIBLE
                                tvShopStatus.text ="Closed"
                            }else{
                                tvShopStatus.visibility = View.VISIBLE
                                tvShopStatus.text ="Open Till  "+ _category.close_hours
                            }
                            if (_category.categories_name != null) {
                                cvCategory.visibility = View.VISIBLE
                                tvCategory.text = _category.categories_name
                            }
                            if(_category.description!=null){
                                tvDescription.visibility = View.VISIBLE
                                tvDescription.text = _category.description
                            }


                        }
                        "U" -> {
                            tvShopStatus.visibility = View.GONE
                            cvCategory.visibility = View.GONE
                        }
                    }

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