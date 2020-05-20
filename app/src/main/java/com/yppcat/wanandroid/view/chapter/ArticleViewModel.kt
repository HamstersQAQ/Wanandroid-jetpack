package com.yppcat.wanandroid.view.chapter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel : ViewModel() {

    val linkUrl = MutableLiveData<String>()

    fun loadArticle(url : String){
        linkUrl.value = url
    }
}
