package com.eshaan.historicalapp.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eshaan.historicalapp.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EVENT = "extra_event"
        const val EXTRA_START_YEAR = "extra_start_year"
        const val EXTRA_END_YEAR = "extra_end_year"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_KEY_FIGURE = "extra_key_figure"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        displayEventDetails()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun displayEventDetails() {
        val event = intent.getStringExtra(EXTRA_EVENT) ?: ""
        val startYear = intent.getIntExtra(EXTRA_START_YEAR, 0)
        val endYear = intent.getIntExtra(EXTRA_END_YEAR, 0)
        val location = intent.getStringExtra(EXTRA_LOCATION) ?: ""
        val keyFigure = intent.getStringExtra(EXTRA_KEY_FIGURE) ?: ""
        val description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: ""

        binding.apply {
            tvEventTitle.text = event
            tvYearRange.text = "$startYear - $endYear"
            tvLocation.text = location
            tvKeyFigure.text = keyFigure
            tvDescription.text = description
            toolbar.title = event
        }
    }
}