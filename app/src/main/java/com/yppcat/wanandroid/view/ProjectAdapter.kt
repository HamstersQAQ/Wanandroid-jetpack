package com.yppcat.wanandroid.view

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yppcat.wanandroid.network.data.ProjectData

class ProjectAdapter(
    resId: Int,
    data: MutableList<ProjectData.Data.Data>
) : BaseQuickAdapter<ProjectData.Data.Data, BaseViewHolder>(resId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProjectData.Data.Data?) {

    }
}