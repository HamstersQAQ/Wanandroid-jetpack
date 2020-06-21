package com.yppcat.wanandroid.view.project

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yppcat.wanandroid.R
import com.yppcat.wanandroid.network.data.ProjectData

class ProjectAdapter(
    resId: Int,
    data: MutableList<ProjectData.Data.Data>
) : BaseQuickAdapter<ProjectData.Data.Data, BaseViewHolder>(resId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProjectData.Data.Data?) {
        helper?.getView<TextView>(R.id.title)?.text = item?.title
        helper?.getView<TextView>(R.id.desc)?.text = item?.desc
        helper?.getView<ImageView>(R.id.pro_img)?.let {
            Glide.with(mContext).load(item?.envelopePic).into(
                it
            )
        }
    }
}