package com.yppcat.wanandroid.view.chapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.model.ArticleModel
import com.yppcat.wanandroid.network.data.ArticleList

class ArticleListViewModel : ViewModel() {

    private var chapterId = 0
    val pageLiveData = MutableLiveData<Int>()
    val dataList = ArrayList<ArticleList.Data.Data>()

    val articleLiveData = Transformations.switchMap(pageLiveData) { page ->
        ArticleModel.getArticles(chapterId, page)
    }

    fun loadArticles(page : Int){
        pageLiveData.value = page
    }

    fun getChapterId(id : Int){
        chapterId = id
    }

}
