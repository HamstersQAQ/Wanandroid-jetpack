package com.yppcat.wanandroid.view.interview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.database.Interview
import com.yppcat.wanandroid.model.InterModel

class InterViewViewModel : ViewModel() {

    private val getLiveData = MutableLiveData<Any?>()

    val list = Transformations.switchMap(getLiveData) {
        InterModel.loadAllInterView()
    }
    var interViewList = ArrayList<Interview>()

    fun loadInterView() {
        getLiveData.value = getLiveData.value
    }

//    fun loadAllInterView() = liveData(Dispatchers.IO) {
//        val result = try {
//            val interResponse = LitePal.findAll<Interview>()
//            Result.success(interResponse)
//        } catch (e: Exception) {
//            Result.failure<MutableList<Interview>>(e
//        }
//        emit(result)
//    }
}
