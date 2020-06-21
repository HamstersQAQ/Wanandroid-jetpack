package com.yppcat.wanandroid.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.model.HomeModel
import com.yppcat.wanandroid.network.data.HomeArticle

class HomeViewModel : ViewModel() {

    private val homeModel: HomeModel by lazy {
        val model = HomeModel()
        model
    }

    private val pageLiveData = MutableLiveData<Int>()
    private val bannerLiveData = MutableLiveData<Any>()

    var alreadyLoadBanner = false

    val articleList = ArrayList<HomeArticle.Data.Data>()


    val articleLiveData = Transformations.switchMap(pageLiveData) { page ->
        homeModel.loadHomeArticle(page)
    }

    val bannerList = Transformations.switchMap(bannerLiveData){
        homeModel.loadBannerData()
    }

    fun loadArticle(page: Int) {
        pageLiveData.value = page
    }

    fun loadBannerData() {
        bannerLiveData.value = bannerLiveData.value
    }

    fun getCurrentPage(): Int? {
        return pageLiveData.value
    }
}