package com.example.myrealtripchallengedemo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myrealtripchallengedemo.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        Log.e("Main Fragment", "Main")

        viewModel.getRssData()

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_text.setOnClickListener {
            viewModel.rssLiveData.observe(viewLifecycleOwner, Observer { result ->
                main_text.text = result
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyDisposable()
    }
}