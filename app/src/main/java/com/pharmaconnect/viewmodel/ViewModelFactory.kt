package com.pharmaconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pharmaconnect.repository.PharmacyRepository

class ViewModelFactory(private val repository: PharmacyRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel() as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository) as T
            modelClass.isAssignableFrom(InventoryViewModel::class.java) -> InventoryViewModel(repository) as T
            modelClass.isAssignableFrom(MedicineFormViewModel::class.java) -> MedicineFormViewModel(repository) as T
            modelClass.isAssignableFrom(OrdersViewModel::class.java) -> OrdersViewModel(repository) as T
            modelClass.isAssignableFrom(ReportsViewModel::class.java) -> ReportsViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
