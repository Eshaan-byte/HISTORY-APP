package com.eshaan.historicalapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eshaan.historicalapp.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEYPASS = "extra_keypass"
    }

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val eventAdapter = EventAdapter { historicalEvent ->
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.Companion.EXTRA_EVENT, historicalEvent.event)
            putExtra(DetailsActivity.Companion.EXTRA_START_YEAR, historicalEvent.startYear)
            putExtra(DetailsActivity.Companion.EXTRA_END_YEAR, historicalEvent.endYear)
            putExtra(DetailsActivity.Companion.EXTRA_LOCATION, historicalEvent.location)
            putExtra(DetailsActivity.Companion.EXTRA_KEY_FIGURE, historicalEvent.keyFigure)
            putExtra(DetailsActivity.Companion.EXTRA_DESCRIPTION, historicalEvent.description)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        val keypass = intent.getStringExtra(EXTRA_KEYPASS) ?: ""
        if (keypass.isNotEmpty()) {
            viewModel.loadDashboard(keypass)
        } else {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = "Invalid keypass"
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = eventAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.dashboardState.observe(this) { state ->
            when (state) {
                is DashboardViewModel.DashboardState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is DashboardViewModel.DashboardState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is DashboardViewModel.DashboardState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.events.observe(this) { events ->
            eventAdapter.submitList(events)
        }
    }
}