package com.yppcat.wanandroid.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yppcat.common.view.banner.Banner
import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeArticleAdapter: HomeArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        activity?.let { activity ->
            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerview.layoutManager = layoutManager
            homeArticleAdapter =
                HomeArticleAdapter(R.layout.item_home_article, viewModel.articleList)
            recyclerview.adapter = homeArticleAdapter
            viewModel.articleLiveData.observe(viewLifecycleOwner, Observer { result ->
                val articleList = result.getOrNull()
                articleList?.let {
                    if (viewModel.getCurrentPage() == 1) {
                        viewModel.articleList.clear()
                    }
                    viewModel.articleList.addAll(articleList)
                    homeArticleAdapter.notifyDataSetChanged()
                    homeArticleAdapter.loadMoreComplete()
                }
            })
            homeArticleAdapter.setOnLoadMoreListener({
                viewModel.getCurrentPage()?.let { page ->
                    viewModel.loadArticle(page + 1)
                }
            }, recyclerview)

            homeArticleAdapter.setOnItemClickListener { adapter, view, position ->
                val action =
                    HomeFragmentDirections.articleToDetail(viewModel.articleList[position].link)
                findNavController().navigate(action)
            }
            viewModel.bannerList.observe(viewLifecycleOwner, Observer { result ->
                val bannerList = result.getOrNull()
//                if (!viewModel.alreadyLoadBanner) {
                    bannerList?.let {
                        val list = ArrayList<String>()
                        it.forEach { data ->
                            list.add(data.imagePath)
                        }
                        if (list.isNotEmpty()) {
                            banner.setDataFromUrl(list)
                        }
                        banner.setOnPageClickListener(object : Banner.onPageClickListener {
                            override fun onPageClick(index: Int) {
                                val action =
                                    HomeFragmentDirections.articleToDetail(it[index].url)
                                findNavController().navigate(action)
                            }

                        })
                        banner.startLoop()
//                    }
                    viewModel.alreadyLoadBanner = true
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.alreadyLoadBanner){
            viewModel.loadArticle(1)
            viewModel.loadBannerData()
            viewModel.alreadyLoadBanner = true
        }
    }
}