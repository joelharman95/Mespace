package com.mespace.ui.view.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_user_item.view.*

import java.util.*

typealias myFriend = (Boolean) -> Unit

class MyFriendsAdapter(val myFriend: myFriend) :
    RecyclerView.Adapter<MyFriendsAdapter.CategoryHolder>() {

    val userList = mutableListOf<HomeScreenResponse.Detail.Userlist>()
    lateinit var navController:NavController

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

    fun addCategoryList(_categoryList: List<HomeScreenResponse.Detail.Userlist>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }



    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {

                if (position <= 6) {
                    userList[position].let { _category ->

                        user_name.text = _category.name
                        border.text=_category.name.first().toString()

                        if(_category.profile_image.isEmpty() || _category.profile_image.contains("no_image")){

                            val rnd = Random()
                            val color: Int =
                                Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                            val strokeWidth = 10
                            val strokeColor = Color.parseColor("#FFFFFF")
                            val gD = GradientDrawable()
                            gD.setColor(color)
                            gD.shape = GradientDrawable.OVAL
                            gD.setStroke(strokeWidth, strokeColor)
                            user_image.background = gD

                        }else{
                            user_image.loadCircularImage(_category.profile_image)
                            border.visibility=View.GONE
                        }


                        setOnClickListener {
                            myFriend.invoke(false)
                        }
                    }
                } else {
                    if (position == 7) {
                        user_name.text = "Show more"
                        border.visibility=View.GONE
                        user_image.loadCircularImage(R.drawable.ic_icon_show_more)
                        setOnClickListener {
                            myFriend.invoke(true)
                        }
                        return
                    } else {
                        user_image.visibility = View.GONE
                        user_name.visibility = View.GONE
                        border.visibility=View.GONE
                    }
                }

                /*if (position <= 7) {
                    if (position == 7) {
                        user_name.text = "Show more"
                        user_image.loadCircularImage(R.drawable.ic_icon_show_more)
                        setOnClickListener {
                            myFriend.invoke(true)
                        }
                    }
                    userList[position].let { _category ->
                        user_name.text = _category.name
                        user_image.loadCircularImage(_category.profile_image)
                    }
                    setOnClickListener {
                        myFriend.invoke(false)
                    }
                } else {
                    user_name.visibility = View.GONE
                    user_image.visibility = View.GONE
                }*/
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
        gd.setStroke(12, Color.WHITE)
        view.background=gd
    }

}