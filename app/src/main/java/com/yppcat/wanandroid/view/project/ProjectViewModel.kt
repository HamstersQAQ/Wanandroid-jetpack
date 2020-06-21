package com.yppcat.wanandroid.view.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.model.ProjectModel
import com.yppcat.wanandroid.network.data.ProjectData

class ProjectViewModel : ViewModel() {

    private val pageLiveData = MutableLiveData<Int>()

    val projectList = ArrayList<ProjectData.Data.Data>()

    val projectLiveData = Transformations.switchMap(pageLiveData) { page ->
        ProjectModel.getProject(page)
    }


    fun loadProject(page: Int) {
        pageLiveData.value = page
    }

    fun getCurrentPage(): Int? {
        return pageLiveData.value
    }
}
