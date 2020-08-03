package com.mespace.ui.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.mespace.R
import com.mespace.data.network.api.request.RequSearchStoreUser
import com.mespace.data.network.api.request.RequSearchUser
import com.mespace.data.network.api.response.StoreUserSearchResponse
import com.mespace.data.viewmodel.SearchViewModel
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), LifecycleObserver {
    private val serachViewModel by viewModel<SearchViewModel>()
var type : String = ""
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



        sear_user_edt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,

                                       before: Int, count: Int) {

                if(type.equals("user"))
                {
                    getUserDetails(s.toString())

                }
                else if(type.equals("store"))
                {
                    getStoreUserList(s.toString(), "second")
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
            getStoreUserList("","first")
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

        user_heading.performClick()
    }

    private fun getStoreUserList(s: String, s1: String) {
        serachViewModel.getStoreUserListItem(RequSearchStoreUser(

                latitude = "11.05617456",
                longitude = "77.0185673",
                start = "0",
                limit = "10",
                keyword = s,
                category_id = "1"
        ), {
            println("User_list" + " " + it.detail.users_list)
            (store_serach_list.adapter as NearStoreUserAdapter).addCategoryList(it.detail.users_list)
            if (s1.equals("first")) {
                setChipsInCategory(it.detail.category_list)

            }
        }, {
            println("User_list" + " " + it)

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
                    if(it.status==1)
                    {
                        (user_list.adapter as SearchUserAdapter).removeCategoryList(it.detail.users_list)

                        (user_list.adapter as SearchUserAdapter).addCategoryList(it.detail.users_list)

                    }
                    else{
                        user_list.visibility = View.GONE
                    }
                }, {
            unblockInput(pbSearch)
        }
        )
    }

    private fun setChipsInCategory(list: List<StoreUserSearchResponse.Detail.Category>) {
        for (item in list) {
            createCategoryChips(item.category_name)
        }
    }

    fun createCategoryChips(categoryName: String) {
        val chip = Chip(context)
        chip.text = categoryName
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
        search_categoryChip.addView(chip as View)
        chip.setOnCloseIconClickListener {

            /*val new = chip.text.removePrefix("#")
            keywordList.remove(new)
            adTag.removeView(chip as View)*/


        }
    }




}
