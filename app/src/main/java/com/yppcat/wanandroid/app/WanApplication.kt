package com.yppcat.wanandroid.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.yppcat.common.network.ServiceCreator
import com.yppcat.wanandroid.util.Preference
import com.yppcat.wanandroid.database.SimpleWorker
import com.yppcat.wanandroid.network.interceptor.AddCookiesInterceptor
import com.yppcat.wanandroid.network.interceptor.ReceivedCookieInterceptor
import okhttp3.OkHttpClient
import org.litepal.LitePal

class WanApplication : Application() {

    companion object {
        var jsData = "js_data"
        lateinit var application: Context
        private var isLoadJSData: Boolean by Preference(
            jsData,
            false
        )
    }


    override fun onCreate() {
        super.onCreate()
        application = applicationContext
        LitePal.initialize(this)
        ServiceCreator.BASE_URL = "https://wanandroid.com"
        ServiceCreator.addInterceptor(
            OkHttpClient.Builder()
                .addInterceptor(ReceivedCookieInterceptor())
                .addInterceptor(AddCookiesInterceptor())
                .build()
        )


        if (!isLoadJSData) {
            Log.d(jsData, "https://wanandroid.com")
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
            WorkManager.getInstance(this).enqueue(request)
            isLoadJSData = true
        }
    }
}