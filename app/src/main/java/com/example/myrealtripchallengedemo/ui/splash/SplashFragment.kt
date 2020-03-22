package com.example.myrealtripchallengedemo.ui.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myrealtripchallengedemo.R
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    companion object {
        const val SPLASH_TIMER = 1300L
    }

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("Splash Fragment", "Splash")

        viewModel.getApplicationVersion()

        Handler().postDelayed( Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }, SPLASH_TIMER)

        viewModel.versionTextLiveData.observe(viewLifecycleOwner, Observer { version ->
            text_app_version.text = version
        })
    }
}