package com.yppcat.wanandroid.view.interview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yppcat.wanandroid.R
import com.yppcat.wanandroid.database.Interview

class InterviewAdapter(
    layoutId: Int,
    data: MutableList<Interview>? = null
) : BaseQuickAdapter<Interview, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: Interview?) {
        item?.let {
            helper?.setText(R.id.inter_text, it.title)
        }
    }

}