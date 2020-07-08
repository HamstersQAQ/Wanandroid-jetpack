package com.yppcat.common.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor(val context: Context) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
//        if (originalResponse.header("Set-Cookie").isNullOrEmpty()){
//            val cookies = HashSet<String>()
//            for (header in )
//        }
        originalResponse.header("Set-Cookie")?.let {
            val cookies = HashSet<String>()
            for (header in it){
                cookies.add(header.toString())
            }
        }
    }
}