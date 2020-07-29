package com.mespace.ui.view.home

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.mespace.R
import com.mespace.data.network.api.request.ReqIsHomePageExists
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (Build.VERSION.SDK_INT >= 21) {
            val window: Window = requireActivity().getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.blue)
        }

        var isShow = true
        var scrollRange = -1
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                near_by_lay.visibility = View.GONE
                isShow = true
            } else if (isShow) {
                near_by_lay.visibility = View.VISIBLE
                isShow = false
            }
        })


        PreferenceManager(requireContext()).apply {
            ivpProfileImage.loadCircularImage(getUserProfile())
            tvUserName.text = getUserName()
        }

        friend_list.adapter = MyFriendsAdapter {
            if (it)
                findNavController().navigate(R.id.action_homeFragment_to_myFriendsFragment)
//                activity?.toast("Show more clicked")
            else
                activity?.toast("Friends clicked")
        }

        store_list.adapter = NearByAdapter {
            if (it)

                findNavController().navigate(R.id.action_homeFragment_to_nearestStoreFragment)
//                activity?.toast("Show more clicked")
            else
                activity?.toast("Stores clicked")
        }
        my_space.adapter = MySpaceAdapter {
            if (it)
                activity?.toast("Add more clicked")
            else
                activity?.toast("Myspace clicked")
        }
        closest_list.adapter = ClosestByAdapter {
            if(it)
                findNavController().navigate(R.id.action_homeFragment_to_closestToYouFragment)
            else
                activity?.toast("Myspace clicked")
        }

        near_by_lay.setOnClickListener {
            if (closest_list.isShown) {
                closest_txt.visibility = View.GONE
                closest_list.visibility = View.GONE
            } else {
                closest_txt.visibility = View.VISIBLE
                closest_list.visibility = View.VISIBLE
            }

        }

        getUserDetails()
    }

    private fun getUserDetails() {
        blockInput(pbHome)
        homeViewModel.getHomePageList((
                ReqIsHomePageExists(
                    userid = "20",
                    latitude = "11.05617456",
                    longitude = "77.0185673"
                )

                ),
            {
                unblockInput(pbHome)

                (closest_list.adapter as ClosestByAdapter).addCategoryList(it.detail.closest_users)

                println("HomeResponse:........${it.detail.userlist}")

                if (it.detail.userlist.size != 0) {
                    friend_list.visibility = View.VISIBLE
                    no_friends.visibility = View.GONE
                    (friend_list.adapter as MyFriendsAdapter).addCategoryList(it.detail.userlist)

                } else {
                    no_friends.visibility = View.VISIBLE
                    friend_list.visibility = View.GONE
                }


                if (it.detail.storelist.size != 0) {
                    (store_list.adapter as NearByAdapter).addCategoryList(it.detail.storelist)

                    store_list.visibility = View.VISIBLE
                    no_shops.visibility = View.GONE
                } else {
                    no_shops.visibility = View.VISIBLE
                    store_list.visibility = View.GONE
                }


                if (it.detail.mespace_list.size != 0) {
                    my_space.visibility = View.VISIBLE
                    add_new_space.visibility = View.GONE
                    (my_space.adapter as MySpaceAdapter).addCategoryList(it.detail.mespace_list)

                } else {
                    add_new_space.visibility = View.VISIBLE
                    my_space.visibility = View.GONE
                }


            }, {
                unblockInput(pbHome)
                activity?.toast(it)
            }
        )

    }

}
