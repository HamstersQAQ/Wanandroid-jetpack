package com.yppcat.wanandroid.view.daily

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.yppcat.wanandroid.app.WanApplication
import com.yppcat.wanandroid.database.Daily
import com.yppcat.wanandroid.model.DailyModel
import com.yppcat.wanandroid.util.DateUtil

class DailyViewModel : ViewModel() {

    private val dailyModel : DailyModel by lazy {
        val model = DailyModel()
        model
    }
    var time: Long = 0
    var writeNote: Boolean = false
    var exercise: Boolean = false
    var reading: Boolean = false
    var readingTime: Int = 0
    var weight: Double = 0.0
    var game: Boolean = false
    var consume: Double = 0.0

    fun saveDaily() {
        dailyModel.let { model ->
            val daily = Daily(
                year = DateUtil.ms2Year(time),
                month = DateUtil.ms2Month(time),
                day = DateUtil.ms2Day(time),
                writeNote = writeNote,
                exercise = exercise,
                reading = reading,
                readingTime = readingTime,
                weight = weight,
                game = game,
                consume = consume
            )
            if (daily != dailyModel.loadLast()){
                model.saveDailyData(daily)
            }else{
                Toast.makeText(WanApplication.application,"今日数据已保存",Toast.LENGTH_SHORT).show()
            }
        }
    }
}