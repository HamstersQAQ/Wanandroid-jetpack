package com.yppcat.wanandroid.view.chapter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yppcat.wanandroid.ChapterAdapter
import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.article_list_fragment.*


class ChapterListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ChapterListFragment()
    }

    private lateinit var viewModel: ChapterListViewModel
    private lateinit var adapter: ChapterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_list_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("ArticleListFragment", "activityCreate")
        viewModel = ViewModelProviders.of(this).get(ChapterListViewModel::class.java)
        val layoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = layoutManager
        activity?.let {
            adapter =
                ChapterAdapter(it, viewModel.chapterList)
            adapter.setOnClickListener(object : ChapterAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    val action =
                        ChapterListFragmentDirections.actionChapterToArticle(viewModel.chapterList[position].id)
                    findNavController().navigate(action)
                }

            })
            recyclerview.adapter = adapter
            viewModel.chapterLiveData.observe(viewLifecycleOwner, Observer { result ->
                val chapters = result.getOrNull()
                chapters?.let { list ->
                    viewModel.chapterList.clear()
                    viewModel.chapterList.addAll(list)
                    adapter.notifyDataSetChanged()
                }

            })
        }
        viewModel.getChapters()
    }

}
