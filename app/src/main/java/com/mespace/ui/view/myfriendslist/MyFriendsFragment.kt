package com.mespace.ui.view.myfriendslist

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
import com.mespace.data.network.api.request.MyUserRequest
import com.mespace.data.viewmodel.MyFriendsListViewModel
import com.mespace.di.toast
import com.mespace.ui.view.home.ClosestByAdapter
import com.mespace.ui.view.home.MyFriendsAdapter
import com.mespace.ui.view.search.SearchUserAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_friends.*
import kotlinx.android.synthetic.main.fragment_my_friends.ivCategoryBack
import kotlinx.android.synthetic.main.fragment_nearest_store.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyFriendsFragment : Fragment(),LifecycleObserver {

    private val myFriendsListViewModel by viewModel<MyFriendsListViewModel>()
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
        return inflater.inflate(R.layout.fragment_my_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getMyFriendsList(start)
        my_friends_list.adapter = MyFavouriteAdapter {
        }
        ivCategoryBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        my_friends_list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    if (!isLoading){
                        val layoutManager=my_friends_list.layoutManager!! as LinearLayoutManager
                        val visibleItemCount = nearest_store_list.layoutManager!!.childCount
                        val totalItemCount = nearest_store_list.layoutManager!!.itemCount
                        val pastVisiblesItems =layoutManager.findFirstVisibleItemPosition();

                        if (visibleItemCount != null) {
                            if ((visibleItemCount + pastVisiblesItems!!) >= totalItemCount!!) {
                                getMyFriendsList(start)
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

    private fun getMyFriendsList(startIndex:Int) {
        myFriendsListViewModel.getMyFriendsList(
            MyUserRequest(
                userId = "1"
            ),{
                it.detail
                (my_friends_list.adapter as MyFavouriteAdapter).addCategoryList(it.detail.user_list)
                isLoading=false
                start += 10

            },{
                isLoading=false
                println("My user list"+ " "+ it)
            }

        )
    }


}