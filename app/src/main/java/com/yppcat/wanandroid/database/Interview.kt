package com.yppcat.wanandroid.database

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class Interview(
    @Column(unique = true, defaultValue = "")
    val title: String, val link: String, val hadStudy: Boolean
) : LitePalSupport() {
    val id: Long = 0
}