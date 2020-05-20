package com.yppcat.wanandroid.view.chapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.model.ArticleModel
import com.yppcat.wanandroid.network.data.Chapter

class ChapterListViewModel : ViewModel() {

    private val getLiveData = MutableLiveData<Any?>()

    val chapterList = ArrayList<Chapter.Data>()

    val chapterLiveData = Transformations.switchMap(getLiveData) { _ ->
        ArticleModel.getChapters()
    }

    fun getChapters(){
        getLiveData.value = getLiveData.value
    }

}
