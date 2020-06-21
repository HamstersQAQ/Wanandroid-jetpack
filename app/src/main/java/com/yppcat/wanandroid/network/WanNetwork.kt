package com.yppcat.wanandroid.network

import com.yppcat.common.network.BaseNetWork
import com.yppcat.common.network.ServiceCreator
import com.yppcat.wanandroid.network.data.*

object WanNetwork : BaseNetWork() {

    private val chapterService = ServiceCreator.create(ChapterService::class.java)

    private val projectService = ServiceCreator.create<ProjectService>()

    suspend fun getChapters(): Chapter = chapterService.getChapters().await()

    suspend fun getArticles(id: Int, page: Int): ArticleList =
        chapterService.getArticleList(id, page).await()

    suspend fun getProjectData(page: Int): ProjectData = projectService.getProjectList(page).await()

    suspend fun getBannerData() : BannerData = projectService.getBannerList().await()

    suspend fun getHomeArticle(page: Int) : HomeArticle = projectService.getHomeArticle(page).await()

    suspend fun  getTopArticle() : TopArticle = projectService.getTopArticle().await()
}