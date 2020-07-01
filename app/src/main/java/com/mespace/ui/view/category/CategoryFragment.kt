package com.mespace.ui.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.mespace.R
import com.mespace.data.network.api.response.ResCategory
import com.mespace.di.blockInput
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategory.adapter = CategoryAdapter { _category ->

        }

        ivCategoryBack.setOnClickListener {
            findNavController().navigateUp()
        }

        getCategoryList()

    }

    private fun getCategoryList() {
        activity?.blockInput(pbCategory)
        //  TODO  ::  ::  Api call here
        val categoryList = mutableListOf<ResCategory>()
        for (i in 1..10)
            categoryList.add(ResCategory(categoryName = "Resturant $i"))
        (rvCategory.adapter as CategoryAdapter).addCategoryList(categoryList)
        activity?.unblockInput(pbCategory)
    }

}
