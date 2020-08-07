package com.mespace.ui.view.businessdetails

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.gson.Gson
import com.mespace.R
import com.mespace.data.network.api.request.AddFavouriteRequest
import com.mespace.data.network.api.request.BusinessDetailsRequest
import com.mespace.data.network.api.request.DeleteFavouriteRequest
import com.mespace.data.network.api.response.StoreDetailsResponse
import com.mespace.data.viewmodel.BusinessDetailsViewmodel
import com.mespace.di.getBitmapFromURL
import com.mespace.di.loadCircularImage
import kotlinx.android.synthetic.main.fragment_business_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class BusinessDetailsFragment : Fragment(), LifecycleObserver {

    private val businessDetailsViewmodel by viewModel<BusinessDetailsViewmodel>()
    private var isChecked = false
    private lateinit var args:BusinessDetailsFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_business_details, container, false
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
             args=BusinessDetailsFragmentArgs.fromBundle(it)
            if (args.storeId.isNotEmpty() && args.userId.isNotEmpty()) {
                getStoreDetails(args.storeId, args.userId)
            }
        }



        if (isChecked) {
            ivFavourite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_icon_favorite_select
                )
            )
        } else {
            ivFavourite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_icon_favorite
                )
            )
        }

         ivFavourite.setOnClickListener {
             if (!isChecked) {
                 businessDetailsViewmodel.addFavourites(
                     AddFavouriteRequest(
                         userId = args!!.userId,
                         spaceId = "S",
                         favUserId = args.storeId
                     ),
                     {
                         isChecked = true
                         ivFavourite.setImageDrawable(
                             ContextCompat.getDrawable(
                                 requireContext(),
                                 R.drawable.ic_icon_favorite_select
                             )
                         )
                         Toast.makeText(context, "Added as favourites", Toast.LENGTH_SHORT).show()
                     }, {
                         Toast.makeText(context, "Not Added as favourites$it", Toast.LENGTH_SHORT)
                             .show()
                     }
                 )
             } else {
                 businessDetailsViewmodel.removeFavourites(
                     DeleteFavouriteRequest(
                         userId = args!!.userId,
                         spaceId = "S",
                         favUserId = args.storeId
                     ),
                     {
                         isChecked = false
                         ivFavourite.setImageDrawable(
                             ContextCompat.getDrawable(
                                 requireContext(),
                                 R.drawable.ic_icon_favorite_select
                             )
                         )
                         Toast.makeText(context, "Remove favourites", Toast.LENGTH_SHORT).show()
                     },{
                         Toast.makeText(context, "Not Remove favourites$it", Toast.LENGTH_SHORT)
                             .show()
                     }
                 )
             }
         }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getStoreDetails(storeId: String, userId: String) {
        businessDetailsViewmodel.getStoreDetails(
            BusinessDetailsRequest(userId, storeId),
            {

                if (it.store_detail.user_profile_image.isEmpty() || it.store_detail.user_profile_image.toString()
                        .contains("no_image")
                ) {
                    drawableColorChange(textLayout)
                    border.text = it.store_detail.name.first().toString()
                } else {
                    ivProfile.loadCircularImage(it.store_detail.user_profile_image)
                    textLayout.visibility = View.GONE
                }


                Glide.with(this)
                    .load(it.store_detail.profile_image)
                    .into(ivStoreBack)


                tvBusinessName.text = it.store_detail.name
                tvBusinessTag.text = it.store_detail.keywords
                tvAddress.text = it.store_detail.location
                tvPhoneNo.text = it.store_detail.phone_no
                tvBusinessDescription.text = it.store_detail.description
                tvRating.text = it.store_detail.ratings.toString()

                if (it.store_detail.is_favourite != "0") {
                    isChecked = true
                    ivFavourite.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_icon_favorite_select
                        )
                    )
                } else {
                    isChecked = false
                    ivFavourite.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_icon_favorite
                        )
                    )
                }
                setChipsInCategory(it.store_detail.category_info)
                if (it.store_detail.open_close != "1") {
                    tvOpenClose.text = "Closed"
                } else {
                    tvOpenClose.text = "Open till " + it.store_detail.open_hours
                    tvOpenClose.setTextColor(resources.getColor(R.color.blue, null))
                }


                val jsonString = Gson().toJson(it)
                var testModel = Gson().fromJson(jsonString, StoreDetailsResponse::class.java)

                println("1111111Actual$it")
                println("1111111String$jsonString")
                println("1111111Converted" + testModel)

            },
            {
                Toast.makeText(context, "bharatttt$it", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setChipsInCategory(list: List<StoreDetailsResponse.Store_detail.Category_info>) {
        for (item in list) {
            createCategoryChips(item)
        }
    }

    private fun createCategoryChips(category: StoreDetailsResponse.Store_detail.Category_info) {
        val chip = Chip(context)
        chip.text = category.category_name
        chip.id = category.category_id.toInt()
        chip.isCloseIconVisible = false
        chip.isClickable = true
        chip.chipStrokeWidth = 5f
        chip.isCheckedIconVisible = false
        chip.isCheckable = false


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
        cgCategory.addView(chip as View)
        /*chip.setOnCheckedChangeListener { chipView, isChecked ->

        }*/

    }

    fun drawableColorChange(view: RelativeLayout) {
        val rnd = Random()
        val color: Int =
            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        view.setBackgroundColor(color)
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.cornerRadius = 100f
        // gd.setStroke(6, Color.BLACK)
        view.background = gd
    }

}