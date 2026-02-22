package com.pharmaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pharmaconnect.repository.PharmacyRepository
import java.util.concurrent.TimeUnit

class DashboardViewModel(repository: PharmacyRepository) : ViewModel() {
    val totalMedicines: LiveData<Int> = repository.getTotalMedicinesCount().asLiveData()
    val lowStockCount: LiveData<Int> = repository.getLowStockCount(10).asLiveData()
    val expiringSoonCount: LiveData<Int> = repository.getExpiringSoonCount(
        System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)
    ).asLiveData()
    val totalOrders: LiveData<Int> = repository.getTotalOrdersCount().asLiveData()
}
