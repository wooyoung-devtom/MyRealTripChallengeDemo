package com.example.myrealtripchallengedemo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myrealtripchallengedemo.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: NewsListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.getRssData()
        adapter = NewsListAdapter(viewModel)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("Main Fragment", "Main")

        news_list.adapter = adapter
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)

        viewModel.newsTextLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        viewModel.stopRefreshLiveEvent.observe(viewLifecycleOwner, Observer {
            news_list_container.isRefreshing = it
        })

        news_list_container.setOnRefreshListener {
            refreshData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyDisposable()
    }

    private fun refreshData() {
        viewModel.getRssData()
    }
}