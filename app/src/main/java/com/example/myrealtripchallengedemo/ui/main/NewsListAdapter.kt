package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealtripchallengedemo.R
import kotlinx.android.synthetic.main.rss_list_item.view.*

class NewsListAdapter(
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e("Adapter", "OnCreateViewHolder")
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rss_list_item, parent, false))
    }

    override fun getItemCount(): Int = viewModel.itemListSize()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rssItem = viewModel.getRssItem(position)
        val newsItem = viewModel.getNewsItem(position)
        holder.itemView.news_title.text = rssItem?.title
        holder.itemView.news_text.text = newsItem?.text
    }
}