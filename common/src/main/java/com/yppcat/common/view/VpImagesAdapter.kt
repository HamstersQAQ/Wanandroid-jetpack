package com.yppcat.common.view

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class VpImagesAdapter(private val mViews: ArrayList<ImageView>) : PagerAdapter() {


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViews[position])
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mViews.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mViews[position])
        return mViews[position]
    }
}