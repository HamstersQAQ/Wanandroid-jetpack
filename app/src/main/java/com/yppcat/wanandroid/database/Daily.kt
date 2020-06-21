package com.yppcat.wanandroid.database

import org.litepal.crud.LitePalSupport

class Daily(
    val year: Int,
    val month: Int,
    val day: Int,
    val writeNote: Boolean,
    val exercise: Boolean,
    val reading: Boolean,
    val readingTime: Int,
    val weight: Double,
    val game: Boolean,
    val consume: Double
) : LitePalSupport() {
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        return if (other is Daily) {
            other.year == this.year && other.month == this.month && other.day == this.day
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + writeNote.hashCode()
        result = 31 * result + exercise.hashCode()
        result = 31 * result + reading.hashCode()
        result = 31 * result + readingTime
        result = 31 * result + weight.hashCode()
        result = 31 * result + game.hashCode()
        result = 31 * result + consume.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}