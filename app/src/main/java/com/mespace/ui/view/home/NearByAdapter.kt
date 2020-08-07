package com.mespace.ui.view.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.HomeScreenResponse
import com.mespace.data.preference.PreferenceManager
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_store_item.view.*
import java.util.*

typealias nearBy = (Boolean) -> Unit

class NearByAdapter(val nearBy: nearBy) :
    RecyclerView.Adapter<NearByAdapter.CategoryHolder>() {

    val userList = mutableListOf<HomeScreenResponse.Detail.Storelist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_store_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
        holder.itemView.border.setOnClickListener {
            PreferenceManager(it.context).apply {
                setStoreId(userList[position].space_id)
            }
            nearBy.invoke(false)
        }
    }

    fun addCategoryList(_categoryList: List<HomeScreenResponse.Detail.Storelist>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                if (position <= 6) {
                    userList[position].let { _category ->
                        user_name.text = _category.name
                        user_distance.text = _category.distance
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

                    }
                    setOnClickListener {
                        PreferenceManager(context).apply {
                            setStoreId(userList[position].space_id)
                        }
                        nearBy.invoke(false)
                    }


                } else {
                    if (position == 7) {
                        user_name.text = "Show more"
                        user_distance.visibility = View.GONE
                        border.visibility=View.GONE
                        user_image.loadCircularImage(R.drawable.ic_icon_show_more)
                        setOnClickListener {
                            nearBy.invoke(true)
                        }
                        return
                    } else {
                        user_image.visibility = View.GONE
                        user_name.visibility = View.GONE
                        user_distance.visibility = View.GONE
                        border.visibility=View.GONE
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
        gd.setStroke(12, Color.WHITE)
        view.background=gd
    }

}