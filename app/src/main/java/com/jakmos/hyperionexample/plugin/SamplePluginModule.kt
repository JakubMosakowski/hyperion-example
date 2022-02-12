package com.jakmos.hyperionexample.plugin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakmos.hyperionexample.R
import com.willowtreeapps.hyperion.plugin.v1.PluginModule
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SamplePluginModule : PluginModule(), View.OnClickListener {

    private var counterJob: Job? = null
    private val ticker = (1..Int.MAX_VALUE)
        .asSequence()
        .asFlow()
        .onEach { delay(DELAY_IN_MILLIS) }

    override fun createPluginView(layoutInflater: LayoutInflater, parent: ViewGroup): View? {
        val view = layoutInflater.inflate(R.layout.sample_plugin, parent, false)
        view.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        Toast.makeText(v?.context, "Our custom plugin has started!", Toast.LENGTH_SHORT).show()

        counterJob?.cancel()
        counterJob = MainScope().launch { ticker.collectLatest(::onTick) }
    }

    private fun onTick(tick: Int) {
        val text = "Seconds passed since plugin start: ${tick * DELAY_IN_MILLIS / 1000}"
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        const val DELAY_IN_MILLIS = 3000L
    }
}
