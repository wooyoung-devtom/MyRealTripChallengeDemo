package com.example.myrealtripchallengedemo.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.myrealtripchallengedemo.R
import com.example.myrealtripchallengedemo.data.dto.NewsBody
import com.example.myrealtripchallengedemo.ui.main.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_news_detail.*

class NewsDetailFragment : Fragment() {
    private var url: String? = null
    private var title: String? = null
    private val webViewClient = WebViewClient()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        url = arguments?.getString("url")
        title = arguments?.getString("title")

        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_detail_toolbar.title = title

        news_web_view.webViewClient = webViewClient
        news_web_view.settings.javaScriptEnabled = true
        news_web_view.loadUrl(url)
    }



}