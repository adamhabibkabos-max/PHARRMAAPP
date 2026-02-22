package com.pharmaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pharmaconnect.model.Order
import com.pharmaconnect.repository.PharmacyRepository
import kotlinx.coroutines.launch

class OrdersViewModel(private val repository: PharmacyRepository) : ViewModel() {
    val orders: LiveData<List<Order>> = repository.getAllOrders().asLiveData()

    fun addDemoOrder(status: String, amount: Double) {
        viewModelScope.launch {
            repository.addOrder(Order(date = System.currentTimeMillis(), status = status, totalAmount = amount))
        }
    }

    fun filterByRange(start: Long, end: Long): LiveData<List<Order>> = repository.getOrdersByDateRange(start, end).asLiveData()
}
