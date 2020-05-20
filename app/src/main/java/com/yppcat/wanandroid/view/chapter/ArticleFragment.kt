package com.yppcat.wanandroid.view.chapter

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.yppcat.wanandroid.R
import kotlinx.android.synthetic.main.article_fragment.*

class ArticleFragment : Fragment() {

    companion object {
        fun newInstance() = ArticleFragment()
    }

    private lateinit var viewModel: ArticleViewModel
    private val args by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        activity?.let {
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
            webSettings.setSupportZoom(true)
            webView.isFocusable = true
            webView.isFocusableInTouchMode = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progressBar?.let {
                        it.progress = newProgress
                        if (newProgress == 100) {
                            it.visibility = View.GONE
                        }
                    }

                }
            }
            viewModel.linkUrl.observe(viewLifecycleOwner, Observer {
                webView.loadUrl(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadArticle(args.linkUrl)
    }
}
