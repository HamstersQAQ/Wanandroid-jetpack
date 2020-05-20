package com.yppcat.common.extends

import android.content.Context
import android.content.Intent

inline fun <reified T>Context.startActivity(){
    val intent = Intent(this,T::class.java)
    startActivity(intent)
}