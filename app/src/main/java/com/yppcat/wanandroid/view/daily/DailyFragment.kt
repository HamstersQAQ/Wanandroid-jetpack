package com.yppcat.wanandroid.view.daily

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.daily_fragment.*

class DailyFragment : Fragment() {

    companion object {
        fun newInstance() = DailyFragment()
    }

    private lateinit var viewModel: DailyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.daily_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DailyViewModel::class.java)

        activity?.let {
            save.setOnClickListener {
                viewModel.time = System.currentTimeMillis()
                viewModel.reading = read_switch.isChecked
                viewModel.readingTime = read_time.text.toString().toInt()
                viewModel.writeNote = write_note.isChecked
                viewModel.game = game.isChecked
                viewModel.consume = consume.text.toString().toDouble()
                viewModel.exercise = exercise.isChecked
                viewModel.weight = weight.text.toString().toDouble()

                viewModel.saveDaily()
            }
        }
    }

}