package com.mespace.ui.view.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.response.CategoryResponse
import com.mespace.data.network.api.response.ResCategory
import kotlinx.android.synthetic.main.layout_category_item.view.*

typealias category = (CategoryResponse.Detail) -> Unit

@Suppress("DEPRECATION")
class CategoryAdapter(val category: category) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    val categoryList = mutableListOf<CategoryResponse.Detail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_category_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addCategoryList(_categoryList: List<CategoryResponse.Detail>) {
        categoryList.addAll(_categoryList)
        notifyDataSetChanged()
    }

    inner class CategoryHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindUi(position: Int) {
            view.apply {
                categoryList[position].let { _category ->
                    tvCategory.text = _category.category_name
                    tvtype.text = "false"



                    setOnClickListener {
                        if (tvtype.text.toString().equals("false"))
                        {
                            tvtype.text = "true"
                            main_lay_cat.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cat_select_shape) );
                        }
                        else{
                            tvtype.text = "false"
                            main_lay_cat.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cat_shape) );
                        }

                            category.invoke(_category)


                    }
                }
            }
        }
    }

}