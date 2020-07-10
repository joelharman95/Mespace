package com.mespace.ui.view.neareststore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mespace.R
import com.mespace.data.viewmodel.NearestStoreListViewModel
import com.mespace.data.viewmodel.SearchViewModel
import com.mespace.di.unblockInput
import com.mespace.ui.view.search.SearchStoreUserAdapter
import kotlinx.android.synthetic.main.fragment_nearest_store.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NearestStoreFragment : Fragment() {

    private val nearestStoreListViewModel by viewModel<NearestStoreListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearest_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nearest_store_list.adapter = SearchStoreUserAdapter {
        }

        getStoreDetails()
    }

    private fun getStoreDetails() {
        nearestStoreListViewModel.getStoreUselist({


            (nearest_store_list.adapter as SearchStoreUserAdapter).addCategoryList(it.detail.storelist)
        }, {

        })
    }
}
