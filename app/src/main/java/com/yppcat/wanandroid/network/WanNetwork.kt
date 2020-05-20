package com.yppcat.wanandroid.network

import com.yppcat.common.network.BaseNetWork
import com.yppcat.common.network.ServiceCreator
import com.yppcat.wanandroid.network.data.ArticleList
import com.yppcat.wanandroid.network.data.Chapter
import com.yppcat.wanandroid.network.data.ProjectData

object WanNetwork : BaseNetWork() {

    private val chapterService = ServiceCreator.create(ChapterService::class.java)

    private val projectService = ServiceCreator.create<ProjectService>()

    suspend fun getChapters(): Chapter = chapterService.getChapters().await()

    suspend fun getArticles(id: Int, page: Int): ArticleList =
        chapterService.getArticleList(id, page).await()

    suspend fun getProjectData(page: Int): ProjectData = projectService.getProjectList(page).await()
}