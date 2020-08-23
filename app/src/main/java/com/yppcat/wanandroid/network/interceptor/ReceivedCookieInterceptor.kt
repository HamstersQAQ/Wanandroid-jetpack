package com.yppcat.wanandroid.network.interceptor

import android.content.Context
import com.yppcat.wanandroid.util.Preference
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor: Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        originalResponse.header("Set-Cookie")?.let {
            val cookies = HashSet<String>()
            for (header in it) {
                cookies.add(header.toString())
            }

            Preference.putStringSet("cookie",cookies)
        }
        return originalResponse
    }
}