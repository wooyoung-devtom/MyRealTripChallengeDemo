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

        Glide.with(holder.itemView).load(newsItem?.thumbnailImg).into(holder.itemView.thumbnail_news)
        holder.itemView.news_title.text = newsItem?.title
        holder.itemView.news_text.text = newsItem?.text
        holder.itemView.setOnClickListener {
            val bundle = bundleOf(
                "url" to newsItem?.link,
                "title" to newsItem?.title
            )
            it.findNavController().navigate(R.id.action_mainFragment_to_newsDetailFragment, bundle)
        }
    }
}