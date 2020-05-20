package com.yppcat.wanandroid.view.chapter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yppcat.wanandroid.ChapterAdapter

import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.article_list_fragment2.*

class ArticleListFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private lateinit var viewModel: ArticleListViewModel
    private val args by navArgs<ArticleListFragmentArgs>()
    private lateinit var adapter: ChapterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_list_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArticleListViewModel::class.java)
        activity?.let { activity ->
            val layoutManager = LinearLayoutManager(activity)
            article_recyclerview.layoutManager = layoutManager
            adapter = ChapterAdapter(activity, viewModel.dataList)
            adapter.setOnClickListener(object : ChapterAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    val action =
                        ArticleListFragmentDirections.actionListToDetail(viewModel.dataList[position].link)
                    findNavController().navigate(action)
                }
            })
            article_recyclerview.adapter = adapter
            swipe_refresh.setOnRefreshListener {
                viewModel.loadArticles(1)
            }
            viewModel.articleLiveData.observe(viewLifecycleOwner, Observer { result ->
                val articleList = result.getOrNull()
                articleList.let { list ->
                    if (viewModel.pageLiveData.value == 1) {
                        viewModel.dataList.clear()
                    }
                    list?.let { viewModel.dataList.addAll(it) }
                    adapter.notifyDataSetChanged()
                    swipe_refresh.isRefreshing = false
                }
            })
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getChapterId(args.chapterId)
        viewModel.loadArticles(1)
    }
}
