package com.pharmaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pharmaconnect.repository.PharmacyRepository
import com.pharmaconnect.utils.DateUtils

data class ReportsUiState(
    val completedCount: Int = 0,
    val failedCount: Int = 0,
    val revenue: Double = 0.0
)

class ReportsViewModel(private val repository: PharmacyRepository) : ViewModel() {

    private val _range = MutableLiveData(Pair(DateUtils.startOfToday(), DateUtils.endOfToday()))

    val uiState: LiveData<ReportsUiState> = Transformations.switchMap(_range) { range ->
        androidx.lifecycle.MediatorLiveData<ReportsUiState>().apply {
            var completed = 0
            var failed = 0
            var revenue = 0.0

            fun emit() {
                value = ReportsUiState(completed, failed, revenue)
            }

            addSource(repository.getCompletedCount(range.first, range.second).asLiveData()) {
                completed = it
                emit()
            }
            addSource(repository.getFailedCount(range.first, range.second).asLiveData()) {
                failed = it
                emit()
            }
            addSource(repository.getRevenue(range.first, range.second).asLiveData()) {
                revenue = it
                emit()
            }
        }
    }

    fun updateRange(start: Long, end: Long) {
        _range.value = Pair(start, end)
    }
}
