package com.mespace.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.mespace.R
import com.mespace.data.preference.PreferenceManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        PreferenceManager(requireContext()).apply {
            ivpProfileImage.loadCircularImage(getUserProfile())
            tvUserName.text = getUserName()
        }

        friend_list.adapter = MyFriendsAdapter {
            if (it)
                activity?.toast("Show more clicked")
            else
                activity?.toast("Friends clicked")
        }

        store_list.adapter = NearByAdapter {
            if (it)
                activity?.toast("Show more clicked")
            else
                activity?.toast("Stores clicked")
        }
        my_space.adapter = MySpaceAdapter {
            if (it)
                activity?.toast("Add more clicked")
            else
                activity?.toast("Myspace clicked")
        }

        getUserDetails()
    }

    private fun getUserDetails() {
        blockInput(pbHome)
        homeViewModel.getUserList(
            {
                unblockInput(pbHome)
                (friend_list.adapter as MyFriendsAdapter).addCategoryList(it.detail.userlist)
                (store_list.adapter as NearByAdapter).addCategoryList(it.detail.storelist)
                (my_space.adapter as MySpaceAdapter).addCategoryList(it.detail.mespace_list)
            }, {
                unblockInput(pbHome)
                activity?.toast(it)
            }
        )

    }

}
