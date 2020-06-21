package com.yppcat.wanandroid.model

import android.util.Log
import androidx.lifecycle.liveData
import com.yppcat.wanandroid.network.WanNetwork
import com.yppcat.wanandroid.network.data.ProjectData
import com.yppcat.wanandroid.network.fire
import com.yppcat.wanandroid.view.project.ProjectFragment
import kotlinx.coroutines.Dispatchers

object ProjectModel {

    fun getProject(page : Int) = fire(Dispatchers.IO){
        val projectResponse  = WanNetwork.getProjectData(page)
        if (projectResponse.errorCode == 0){
            val projects = projectResponse.data
            Result.success(projects)
        }else{
            Result.failure(RuntimeException("response status is ${projectResponse.errorMsg}"))
        }
    }

//    fun getProject(page: Int) = liveData<ProjectData.Data>(Dispatchers.IO){
//        val projectResponse  = WanNetwork.getProjectData(page)
//        if (projectResponse.errorCode == 0){
//            val projects = projectResponse.data
//            Log.d("ProjectFragment", "onActivity: " + projects.size)
//            Result.success(projects)
//
//        }else{
//            Result.failure(RuntimeException("response status is ${projectResponse.errorMsg}"))
//        }
//    }
}