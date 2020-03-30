package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrealtripchallengedemo.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.rss_list_item.view.*

class NewsListAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rss_list_item, parent, false))
    }

    override fun getItemCount(): Int = viewModel.itemListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItem = viewModel.getNewsItem(position)

        Glide.with(holder.itemView).asBitmap().load(newsItem.imgUrl).into(holder.itemView.thumbnail_news)
        holder.itemView.news_title.text = newsItem.newsBody?.title
        holder.itemView.news_text.text = newsItem.description
        holder.itemView.keyword_first.text = newsItem.keyWord?.firstKey!!
        holder.itemView.keyword_second.text = newsItem.keyWord.secondKey!!
        holder.itemView.keyword_third.text = newsItem.keyWord.thirdKey!!
        holder.itemView.setOnClickListener {
            val bundle = bundleOf(
                "url" to newsItem.newsBody?.link,
                "title" to newsItem.newsBody?.title,
                "firstKey" to newsItem.keyWord.firstKey,
                "secondKey" to newsItem.keyWord.secondKey,
                "thirdKey" to newsItem.keyWord.thirdKey
            )
            it.findNavController().navigate(R.id.action_mainFragment_to_newsDetailFragment, bundle)
        }
    }
}