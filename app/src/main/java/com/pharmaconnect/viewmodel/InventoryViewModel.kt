package com.pharmaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.pharmaconnect.model.Medicine
import com.pharmaconnect.repository.PharmacyRepository
import kotlinx.coroutines.flow.first

class InventoryViewModel(private val repository: PharmacyRepository) : ViewModel() {
    private val query = MutableLiveData("")

    val medicines: LiveData<List<Medicine>> = liveData {
        query.asFlow().collect { q ->
            val source = if (q.isBlank()) repository.getAllMedicines() else repository.searchMedicines(q)
            emit(source.first())
        }
    }

    fun search(text: String) {
        query.value = text
    }

    fun filteredByCategory(category: String?): LiveData<List<Medicine>> =
        repository.getAllMedicines().asLiveData().let { liveData ->
            androidx.lifecycle.Transformations.map(liveData) { list ->
                when {
                    category.isNullOrBlank() -> list
                    else -> list.filter { it.category.equals(category, true) }
                }
            }
        }

    fun lowStockOnly(): LiveData<List<Medicine>> = androidx.lifecycle.Transformations.map(repository.getAllMedicines().asLiveData()) {
        it.filter { medicine -> medicine.quantity <= 10 }
    }

    fun expiredOnly(): LiveData<List<Medicine>> = androidx.lifecycle.Transformations.map(repository.getAllMedicines().asLiveData()) {
        val now = System.currentTimeMillis()
        it.filter { medicine -> medicine.expiryDate < now }
    }
}
