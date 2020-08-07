package com.mespace.ui.view.neareststore

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.NearByStoreResponse
import com.mespace.data.preference.PreferenceManager
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.layout_near_store_item_list.view.*

import java.util.*


typealias storeUser = (Boolean) -> Unit

class NearByStoreAdapter(val storeUser: storeUser) :
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<NearByStoreResponse.Detail.Store_list>) {
        userList.addAll(_categoryList)
        notifyDataSetChanged()
    }



    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        fun bindUi(position: Int) {
            view.apply {

                userList[position].let { _category ->

                    if(_category.profile_image.isEmpty() || _category.profile_image.toString().contains("no_image")){
                        val rnd = Random()
                        val color: Int =
                            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                        val strokeWidth = 10
                        val strokeColor = Color.parseColor("#FFFFFF")
                        val gD = GradientDrawable()
                        gD.setColor(color)
                        gD.shape = GradientDrawable.OVAL
                        gD.setStroke(strokeWidth, strokeColor)
                        ivStorePic.background = gD
                        border.text=_category.name.first().toString()
                    }else{
                        ivStorePic.loadCircularImage(_category.profile_image)
                        border.visibility=View.GONE
                    }

                    tvStoreName.text = _category.name
                    tvStoreDistance.text = _category.distance
                    tvStoreTag.text=_category.tag_name
                    tvStoreCategory.text=_category.categories
                    tvStoreDescription.text=_category.description
                    if(_category.open_close!="1"){
                        tvStoreStatus.text=context.getString(R.string.closed)
                        tvStoreStatus.setTextColor(resources.getColor(R.color.light_color, null))
                    }else{
                        tvStoreStatus.setTextColor(resources.getColor(R.color.blue, null))
                        tvStoreStatus.text="Open till "+_category.close_hours
                    }

                    setOnClickListener {
                        PreferenceManager(context).apply {
                            setStoreId(_category.space_id.toString())
                        }
                        storeUser.invoke(true)
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