package com.eshaan.historicalapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshaan.historicalapp.data.model.HistoricalEvent
import com.eshaan.historicalapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _dashboardState = MutableLiveData<DashboardState>()
    val dashboardState: LiveData<DashboardState> = _dashboardState

    private val _events = MutableLiveData<List<HistoricalEvent>>()
    val events: LiveData<List<HistoricalEvent>> = _events

    fun loadDashboard(keypass: String) {
        _dashboardState.value = DashboardState.Loading

        viewModelScope.launch {
            repository.getDashboard(keypass)
                .onSuccess { events ->
                    _events.value = events
                    _dashboardState.value = DashboardState.Success
                }
                .onFailure { throwable ->
                    _dashboardState.value = DashboardState.Error(throwable.message ?: "Failed to load dashboard")
                }
        }
    }

    sealed class DashboardState {
        object Loading : DashboardState()
        object Success : DashboardState()
        data class Error(val message: String) : DashboardState()
    }
}