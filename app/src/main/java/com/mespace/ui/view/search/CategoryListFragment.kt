package com.mespace.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.gson.Gson
import com.mespace.R
import com.mespace.data.network.api.response.Category
import com.mespace.data.network.api.response.CategoryListResponses
import kotlinx.android.synthetic.main.fragment_category_list.*


class CategoryListFragment : Fragment() {


    private lateinit var args: CategoryListFragmentArgs
    private var categoryList = ArrayList<Category>()
    private var checkedList= ArrayList<Category>()
    private val checkedCategoryList: HashMap<Int, String> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.let {

            args = CategoryListFragmentArgs.fromBundle(it!!)
            if (args.categoryResponse.isNotEmpty()) {
                args.categoryResponse
                val categoryListResponses =
                    Gson().fromJson(args.categoryResponse, CategoryListResponses::class.java)
                checkedList = categoryListResponses.checkedCategory as ArrayList<Category>
                categoryList = categoryListResponses.categoryList as ArrayList<Category>
                for(item in checkedList){
                    checkedCategoryList[item.category_id.toInt()] = item.category_name
                }
                setChipsInCategory(categoryList)
            }
        }

        ivCancel.setOnClickListener {
            backToSearchFragment()
        }

    }

    private fun backToSearchFragment(){

        val searchResponse= CategoryListResponses(categoryList,checkedList)
        val searchResponseString = Gson().toJson(searchResponse)

        var action =CategoryListFragmentDirections.actionCategoryListFragmentToSearchFragment(searchResponseString)
        findNavController().navigate(action)

    }


    private fun setChipsInCategory(list: List<Category>) {
        for (item in list) {
            createCategoryChips(item)
        }
    }

    fun createCategoryChips(category: Category) {
        val chip = Chip(context)
        chip.text = category.category_name
        chip.id = category.category_id.toInt()
        chip.isCloseIconVisible = false
        chip.isClickable = true
        chip.chipStrokeWidth = 5f
        chip.isCheckedIconVisible = false

        val chipDrawable: ChipDrawable? = context?.let {
            ChipDrawable.createFromAttributes(
                it,
                null,
                0,
                R.style.ChipsStyle
            )
        }
        if (chipDrawable != null) {
            chip.setChipDrawable(chipDrawable)
        }

        if(checkedCategoryList.containsKey(category.category_id.toInt())){
            chip.isChecked=true
        }

        chip.setOnCheckedChangeListener { chipView, isChecked ->
            if (isChecked) {
                checkedCategoryList[chipView.id] = chipView.text.toString()
                checkedList.add(
                    Category(
                        chipView.id.toString(),
                        chipView.text.toString()
                    )
                )
            } else {
                if (checkedCategoryList.containsKey(chipView.id)) {
                    checkedCategoryList.remove(chipView.id)
                    checkedList.remove(
                        Category(
                            chipView.id.toString(),
                            chipView.text.toString()
                        )
                    )
                }
            }
        }
        cgCategoryList.addView(chip as View)
    }


}