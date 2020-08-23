package com.yppcat.wanandroid.util

import android.content.Context
import android.content.SharedPreferences
import com.yppcat.wanandroid.app.WanApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preference<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        val preference: SharedPreferences by lazy {
            WanApplication.application.getSharedPreferences("wan_preference", Context.MODE_PRIVATE)
        }

        fun clear() {
            preference.edit().clear().apply()
        }

        fun putStringSet(key: String, set: Set<String>) = with(preference.edit()) {
            putStringSet(key, set)
        }.apply()

        fun  getStringSe(key: String,default : Set<String>) : Set<String>? = with(preference){
            val value = getStringSet(key,default)
            value
        }

    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharedPreferences(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        return putSharedPreferences(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getSharedPreferences(key: String, default: T): T = with(preference) {
        val value: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)!!
            is Float -> getFloat(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }
        value as T
    }

    private fun <T> putSharedPreferences(key: String, default: T) = with(preference.edit()) {
        when (default) {
            is Long -> putLong(key, default)
            is String -> putString(key, default)
            is Float -> putFloat(key, default)
            is Int -> putInt(key, default)
            is Boolean -> putBoolean(key, default)
            else -> throw IllegalArgumentException("This type of data can not be saved! ")
        }.apply()
    }

}