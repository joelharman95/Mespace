package com.mespace.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.mespace.R
import com.mespace.data.viewmodel.HomeViewModel
import com.mespace.di.blockInput
import com.mespace.di.loadCircularImage
import com.mespace.di.toast
import com.mespace.di.unblockInput
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), LifecycleObserver {
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friend_list.adapter = MyFriendsAdapter({

        })
        getUserdetails()
    }

    private fun getUserdetails() {
        blockInput(pbHome)
        homeViewModel.getUserList(
            {
                unblockInput(pbHome)
                println(" Data " + it.detail.mespace_list)
                profile_image.loadCircularImage("http://mespace.know3.com/public/uploads/common/images/profile_setup_pic@3x.png")
                (friend_list.adapter as MyFriendsAdapter).addCategoryList(it.detail.userlist)
            }, {
                unblockInput(pbHome)
                activity?.toast(it)

            }
        )

    }

}
