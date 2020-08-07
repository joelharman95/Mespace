package com.mespace.ui.view.search

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.UserSearchResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_search_user_item.view.*

import java.util.*

typealias myUser = (Boolean) -> Unit

class SearchUserAdapter(val user: myUser) :
    RecyclerView.Adapter<SearchUserAdapter.CategoryHolder>() {

    val userList = mutableListOf<UserSearchResponse.Detail.Users>()

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

    fun addCategoryList(_categoryList: List<UserSearchResponse.Detail.Users>) {
        userList.removeAll(_categoryList)
        userList.clear()
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }


    fun removeCategoryList(_categoryList: List<UserSearchResponse.Detail.Users>) {

        userList.removeAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {


                    userList[position].let { _category ->

                        if(_category.profile_image.isEmpty() || _category.profile_image.toString().contains("no_image")){
                            drawableColorChange(rlBackground)
                            initialText.text=_category.name.first().toString()
                        }else{
                            ivUserPic.loadCircularImage(_category.profile_image)
                            rlBackground.visibility=View.GONE
                        }

                        ivUserPic.loadCircularImage(_category.profile_image)
                        tvUserName.text = _category.name
                        tvTag.text = _category.keywords
                        tvDis.text = _category.distance

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