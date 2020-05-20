package com.yppcat.wanandroid.view.interview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter

import com.yppcat.wanandroid.R
import com.yppcat.wanandroid.view.chapter.ArticleListFragmentDirections
import kotlinx.android.synthetic.main.inter_view_fragment.*

class InterViewFragment : Fragment() {

    companion object {
        fun newInstance() = InterViewFragment()
    }

    private lateinit var viewModel: InterViewViewModel
    private lateinit var interviewAdapter: InterviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.inter_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InterViewViewModel::class.java)
        activity?.let {
            val layoutManager = LinearLayoutManager(it)
            inter_recyclerView.layoutManager = layoutManager
            interviewAdapter = InterviewAdapter(R.layout.item_inter, viewModel.interViewList)
            interviewAdapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                    val action =
                        ArticleListFragmentDirections.actionListToDetail(viewModel.interViewList[position].link)
                    findNavController().navigate(action)
                }
            inter_recyclerView.adapter = interviewAdapter

        }
        viewModel.list.observe(viewLifecycleOwner, Observer {
            val interViewList = it.getOrNull()
            interViewList?.let { list ->
                viewModel.interViewList.clear()
                viewModel.interViewList.addAll(list)
                interviewAdapter.notifyDataSetChanged()
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadInterView()
    }
}
