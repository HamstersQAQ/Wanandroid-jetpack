package com.yppcat.wanandroid.view.home

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yppcat.wanandroid.R
import com.yppcat.wanandroid.network.data.HomeArticle

class HomeArticleAdapter(
    layoutId: Int,
    data: MutableList<HomeArticle.Data.Data>? = null
) : BaseQuickAdapter<HomeArticle.Data.Data, BaseViewHolder>(layoutId,data) {
    override fun convert(helper: BaseViewHolder?, item: HomeArticle.Data.Data?) {
        helper?.getView<TextView>(R.id.title)?.text = item?.title
        helper?.getView<TextView>(R.id.author)?.text = item?.author
    }
}