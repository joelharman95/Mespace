package com.mespace.ui.view.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mespace.R
import com.mespace.data.network.api.request.MySpaceRequest
import com.mespace.data.viewmodel.MySpaceBottomViewModel


import kotlinx.android.synthetic.main.fragment_my_space_bottom.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MySpaceBottomFragment : BottomSheetDialogFragment(), LifecycleObserver {

    private val mySpaceBottomViewModel by viewModel<MySpaceBottomViewModel>()

    val startIndex:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_space_bottom, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMySpaceList()
        rvMySpace.adapter = MySpaceBottomAdapter {
        }
    }

    fun getMySpaceList() {
        /*PreferenceManager(requireContext()).apply {
            ivpProfileImage.loadCircularImage(getUserProfile())
            tvUserName.text = getUserName()
        }*/
        mySpaceBottomViewModel.getMySpaceList(
            MySpaceRequest(
                userId = "1",
                start = startIndex,
                limit = "20"),
            {
                (rvMySpace.adapter as MySpaceBottomAdapter).addCategoryList(it.detail.my_space)
                println("Bharathhhhhh"+it.detail.my_space)
            },
            {
                println("Bharathhhhhh"+it)
            })

    }


}