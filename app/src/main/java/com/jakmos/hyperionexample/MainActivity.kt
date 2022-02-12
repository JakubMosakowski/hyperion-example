package com.jakmos.hyperionexample

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jakmos.hyperionexample.databinding.ActivityMainBinding
import timber.log.Timber
import java.time.Instant
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    override fun onResume() {
        super.onResume()
        setupText()
    }

    private fun setupText() = with(binding) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        sharedPrefText.text = sharedPref.getString(SHARED_PREF_KEY, "")
    }

    private fun setupButtons() = with(binding) {
        crashButton.setOnClickListener {
            throw RuntimeException("Oh no... the button crashed the app.")
        }

        addSharedPrefButton.setOnClickListener {
            Timber.v("Add shared prefs button clicked!")
            updatePreferences()
        }

        startAnimationButton.setOnClickListener {
            animateViews()
        }
    }

    private fun updatePreferences() {
        getPreferences(Context.MODE_PRIVATE)
            .edit()
            .apply {
                val currentDate = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                putString("KEY", currentDate)
                apply()

                setupText()
            }
    }

    @SuppressLint("InflateParams")
    private fun animateViews() {
        Timber.v("Start animation button clicked!")
        (0..1000).forEach {
            val inflater = LayoutInflater.from(this)
            val imageView = inflater.inflate(R.layout.dot, null, false)
            imageView.layoutParams = LinearLayout.LayoutParams(3, 3)
                .apply { setMargins(it * 5, it * 5, 0, 0) }

            binding.container.addView(imageView)
            ObjectAnimator.ofFloat(imageView, "translationY", 0f, 2000f).apply {
                duration = 1000
                start()
            }
        }
    }

    private companion object {
        const val SHARED_PREF_KEY = "KEY"
    }
}
