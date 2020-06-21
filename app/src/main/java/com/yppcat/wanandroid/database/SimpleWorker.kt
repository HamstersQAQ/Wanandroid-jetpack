package com.yppcat.wanandroid.database

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.jsoup.Jsoup
import org.litepal.LitePal
import java.io.IOException

class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val TAG = "SimpleWorker"
    }

    override fun doWork(): Result {
        try {
            LitePal.getDatabase()
            val document =
                Jsoup.connect("https://github.com/Moosphan/Android-Daily-Interview").get()
            val links = document.getElementsByTag("li")
            for (element in links) {
                if (element.childNode(0).toString().contains("？")) {
                    val index = element.childNode(0).toString().substring(8, 15)
                    val rootElement = element.allElements[0]
                    val linkUrl = rootElement.select("a[href]").attr("href")
                    val titleTotal = rootElement.select("a[href]")[0].childNode(0).toString()
                    Log.d(TAG, "doWork: $titleTotal")
                    val interview = Interview( title = titleTotal, link = linkUrl,hadStudy = false)
                    interview.save()
                    Log.d(TAG, "index = $index  linkUrl = $linkUrl   title = $titleTotal")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Result.success()
    }
}