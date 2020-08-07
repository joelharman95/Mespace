package com.mespace.ui.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.gson.Gson
import com.mespace.R
import com.mespace.data.network.api.request.RequSearchStoreUser
import com.mespace.data.network.api.request.RequSearchUser
import com.mespace.data.network.api.response.Category
import com.mespace.data.network.api.response.CategoryListResponses
import com.mespace.data.viewmodel.SearchViewModel
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), LifecycleObserver {

    private val serachViewModel by viewModel<SearchViewModel>()
    var type: String = ""
    private val checkedCategoryList: HashMap<Int, String> = HashMap()
    private var categoryList = ArrayList<Category>()
    private var checkedCategory = ArrayList<Category>()
    private lateinit var args: SearchFragmentArgs
    var categoryString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_list.adapter = SearchUserAdapter {
        }

        store_serach_list.adapter = NearStoreUserAdapter {
        }

        ivCategoryBack.setOnClickListener {
            findNavController().navigateUp()
        }

        sear_user_edt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,

                before: Int, count: Int
            ) {

                if (type.equals("user")) {
                    getUserDetails(s.toString())

                } else if (type.equals("store")) {
                    getStoreUserList(s.toString(),"", "second")
                }

            }
        })

        store_heading.setOnClickListener {
            type = "store"
            user_lay.visibility = View.GONE
            store_lay.visibility = View.VISIBLE
            txt_store.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
            txt_user.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            vi_store.visibility = View.VISIBLE
            vi_user.visibility = View.GONE
            println("Store_heading" + " " + " Store serach")
            getStoreUserList("","", "first")
        }

        user_heading.setOnClickListener {
            type = "user"
            store_lay.visibility = View.GONE
            user_lay.visibility = View.VISIBLE
            txt_store.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            txt_user.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
            vi_user.visibility = View.VISIBLE
            vi_store.visibility = View.GONE
            getUserDetails("")
        }

        tvMore.setOnClickListener {

            val searchResponse = CategoryListResponses(categoryList, checkedCategory)
            val searchResponseString = Gson().toJson(searchResponse)

            var action = SearchFragmentDirections.actionSearchFragmentToCategoryListFragment(
                searchResponseString
            )

            findNavController().navigate(action)
        }

        cvMore.setOnClickListener { }

        arguments.let {

            args = SearchFragmentArgs.fromBundle(it!!)

            if (args.categorResponse.isNotEmpty() && args.categorResponse != "default") {

                val categoryListResponses =
                    Gson().fromJson(args.categorResponse, CategoryListResponses::class.java)
                checkedCategory = categoryListResponses.checkedCategory as ArrayList<Category>

                getCategoryString(checkedCategory)
                for (item in checkedCategory) {
                    if (!(checkedCategoryList.containsKey(item.category_id.toInt()))) {
                        checkedCategoryList.put(item.category_id.toInt(), item.category_name)
                    }
                    categoryString = if (categoryString.isEmpty()) {
                        item.category_id
                    } else {
                        categoryString + "," + item.category_id
                    }
                    createSelectedCategoryChip(item)
                }

                getStoreUserList("",categoryString, "first")


            } else {
                user_heading.performClick()
            }
        }

    }

    private fun getCategoryString(list:List<Category>):String
    {
        categoryString=""
        for (item in list) {
            categoryString = if (categoryString.isEmpty()) {
                item.category_id
            } else {
                categoryString + "," + item.category_id
            }
        }
        return categoryString
    }


    private fun getStoreUserList(keyword: String,categoryId:String, s1: String) {
        serachViewModel.getStoreUserListItem(RequSearchStoreUser(

            latitude = "11.05617456",
            longitude = "77.0185673",
            start = "0",
            limit = "10",
            keyword = keyword,
            category_id = categoryId
        ), {
            unblockInput(pbSearch)

            (store_serach_list.adapter as NearStoreUserAdapter).addCategoryList(it.detail.users_list)
            if (s1.equals("first")) {
                search_categoryChip.removeAllViews()
                if (it.detail.category_list.size > 1) {
                    cvMore.visibility = View.VISIBLE
                    tvMore.visibility = View.VISIBLE
                }
                categoryList.clear()
                categoryList.addAll(it.detail.category_list)
                setChipsInCategory(it.detail.category_list)
            }

        }, {
            println("User_list" + " " + it)
            unblockInput(pbSearch)
        })
    }


    private fun getUserDetails(s: String) {
        serachViewModel.getUserList(RequSearchUser(
            latitude = "11.05617456",
            longitude = "77.0185673",
            start = "0",
            limit = "10",
            keyword = s


        ),
            {
                unblockInput(pbSearch)
                if (it.status == 1) {
                    (user_list.adapter as SearchUserAdapter).removeCategoryList(it.detail.users_list)

                    (user_list.adapter as SearchUserAdapter).addCategoryList(it.detail.users_list)

                } else {
                    user_list.visibility = View.GONE
                }
            }, {
                unblockInput(pbSearch)
            }
        )
    }

    private fun setChipsInCategory(list: List<Category>) {
        for ((index, item) in list.withIndex()) {
            if (index < 2) {
                createCategoryChips(item)
            }
        }
    }

    fun createSelectedCategoryChip(category: Category) {
        val chip = Chip(context)
        chip.text = category.category_name
        chip.id = category.category_id.toInt()
        chip.isClickable = true
        chip.chipStrokeWidth = 5f
        chip.isCheckedIconVisible = false
        chip.isCheckable = false
        val chipDrawable: ChipDrawable? = context?.let {
            ChipDrawable.createFromAttributes(
                it,
                null,
                0,
                R.style.SelectedChip
            )
        }
        if (chipDrawable != null) {
            chip.setChipDrawable(chipDrawable)
        }
        chip.setOnCloseIconClickListener {
            if (checkedCategoryList.containsKey(it.id)) {
                checkedCategoryList.remove(it.id)
                checkedCategory.remove(
                    Category(
                        chip.id.toString(),
                        chip.text.toString()
                    )
                )
            }
            selectedChipGroup.removeView(chip as View)
            getStoreUserList("",getCategoryString(checkedCategory), "first")
        }
        selectedChipGroup.addView(chip as View)
    }

    private fun createCategoryChips(category: Category) {
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
        chip.setOnCheckedChangeListener { chipView, isChecked ->
            if (isChecked) {
                if (!checkedCategoryList.containsKey(chipView.id)){
                    checkedCategoryList[chipView.id] = chipView.text.toString()
                    val category=Category(
                        chipView.id.toString(),
                        chipView.text.toString()
                    )
                    checkedCategory.add(
                        category
                    )
                    createSelectedCategoryChip(category)
                    getStoreUserList("",getCategoryString(checkedCategory),"first")

                }

            } else {
                if (checkedCategoryList.containsKey(chipView.id)) {
                    checkedCategoryList.remove(chipView.id)
                    checkedCategory.remove(
                        Category(
                            chipView.id.toString(),
                            chipView.text.toString()
                        )
                    )
                    getStoreUserList("",getCategoryString(checkedCategory),"first")
                }
            }
        }
        search_categoryChip.addView(chip as View)
    }


}
