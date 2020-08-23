package com.yppcat.wanandroid.network.interceptor

import com.yppcat.wanandroid.util.Preference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder: Request.Builder = chain.request().newBuilder()
        val cookies = Preference.getStringSe("cookie", HashSet<String>())
        cookies?.let {
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}