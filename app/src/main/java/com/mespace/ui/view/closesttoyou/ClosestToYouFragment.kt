package com.mespace.ui.view.closesttoyou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mespace.R
import com.mespace.data.network.api.request.ClosestToRequest
import com.mespace.data.viewmodel.ClosestToViewModel
import com.mespace.data.viewmodel.MyFriendsListViewModel
import com.mespace.ui.view.myfriendslist.MyFavouriteAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_friends.*
import kotlinx.android.synthetic.main.fragment_my_friends.ivCategoryBack
import kotlinx.android.synthetic.main.fragment_nearest_store.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ClosestToYouFragment : Fragment(), LifecycleObserver {


    private val closestToViewModel by viewModel<ClosestToViewModel>()
    private var start:Int=0
    var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closest_to_you, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getClosestToYou(start)
        closest_list.adapter = ClosestToAdapter {
        }
        ivCategoryBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        closest_list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    if (!isLoading){
                        val layoutManager=closest_list.layoutManager!! as LinearLayoutManager
                        val visibleItemCount = closest_list.layoutManager!!.childCount
                        val totalItemCount = closest_list.layoutManager!!.itemCount
                        val pastVisiblesItems =layoutManager.findFirstVisibleItemPosition();

                        if (visibleItemCount != null) {
                            if ((visibleItemCount + pastVisiblesItems!!) >= totalItemCount!!) {
                                getClosestToYou(start)
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

    private fun getClosestToYou(startIndex:Int) {

        closestToViewModel.getClosestTo(
            ClosestToRequest(
                userId = "15",
                longitude = "77.34294143494428",
                latitude = "11.050590515136719"
            ),
            {
                println("dfgdfhgtfk" + it.detail.users_list)
                (closest_list.adapter as ClosestToAdapter).addCategoryList(it.detail.users_list)
                isLoading=false
                start += 10

            },
            {
                isLoading=false
                println("My user list"+ " "+ it)
            }
        )

    }

}