package com.mespace.ui.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mespace.R
import com.mespace.data.viewmodel.HomeViewModel
import com.mespace.data.viewmodel.SearchViewModel
import com.mespace.di.toast
import com.mespace.di.unblockInput
import com.mespace.ui.view.home.MyFriendsAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), LifecycleObserver {
    private val serachViewModel by viewModel<SearchViewModel>()

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

        store_serach_list.adapter = SearchStoreUserAdapter {
        }
        store_heading.setOnClickListener {

            user_lay.visibility = View.GONE
            store_lay.visibility = View.VISIBLE
            getStoreDetails()
        }

        user_heading.setOnClickListener {

            store_lay.visibility = View.GONE
            user_lay.visibility = View.VISIBLE
            getUserDetails()
        }

        user_heading.performClick()
    }

    private fun getStoreDetails() {
        serachViewModel.getStoreUselist({

            unblockInput(pbSearch)
            (store_serach_list.adapter as SearchStoreUserAdapter).addCategoryList(it.detail.storelist)
        }, {

            unblockInput(pbSearch)
        })
    }

    private fun getUserDetails() {
        serachViewModel.getUserList(
            {
                unblockInput(pbSearch)
                (user_list.adapter as SearchUserAdapter).addCategoryList(it.detail.userlist)
            }, {
                unblockInput(pbSearch)
            }
        )
    }

}
