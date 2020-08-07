package com.mespace.ui.view.neareststore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.request.NearByStoreRequest
import com.mespace.data.preference.PreferenceManager
import com.mespace.data.viewmodel.NearestStoreListViewModel
import com.mespace.di.toast
import com.mespace.ui.view.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.fragment_nearest_store.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NearestStoreFragment : Fragment(), LifecycleObserver {

    private var start:Int=0
    var isLoading = false

    private val nearestStoreListViewModel by viewModel<NearestStoreListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nearest_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nearest_store_list.adapter = NearByStoreAdapter {
            if(it){
                var storeId = ""
                var userId = ""
                PreferenceManager(requireContext()).apply {
                    storeId = getStoreId()
                    userId = getUserId()
                }
                val action = NearestStoreFragmentDirections.actionNearestStoreFragmentToBusinessDetailsFragment(storeId,userId)
                findNavController().navigate(action)
            }

        }
        getStoreDetails(start)
        ivCategoryBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        nearest_store_list.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    if (!isLoading){
                        val layoutManager=nearest_store_list.layoutManager!! as LinearLayoutManager
                        val visibleItemCount = nearest_store_list.layoutManager!!.childCount
                        val totalItemCount = nearest_store_list.layoutManager!!.itemCount
                        val pastVisiblesItems =layoutManager.findFirstVisibleItemPosition();

                        if (visibleItemCount != null) {
                            if ((visibleItemCount + pastVisiblesItems!!) >= totalItemCount!!) {
                                getStoreDetails(start)
                                isLoading=true
                            }
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun getStoreDetails(startIndex:Int ) {
        nearestStoreListViewModel.getStoreUselist(NearByStoreRequest(
            userId = "1",
            longitude = "77.0185673",
            latitude = "11.05617456",
            start = startIndex,
            limit = "10"
        ), {
            (nearest_store_list.adapter as NearByStoreAdapter).addCategoryList(it.detail.store_list)
            isLoading=false
            start += 10
        }, {
            isLoading=false
        })
    }
}
