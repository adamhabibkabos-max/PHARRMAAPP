package com.pharmaconnect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharmaconnect.model.Medicine
import com.pharmaconnect.repository.PharmacyRepository
import kotlinx.coroutines.launch

class MedicineFormViewModel(private val repository: PharmacyRepository) : ViewModel() {
    private val _medicine = MutableLiveData<Medicine?>()
    val medicine: LiveData<Medicine?> = _medicine

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun loadMedicine(id: Long) {
        viewModelScope.launch {
            _medicine.value = repository.getMedicineById(id)
        }
    }

    fun saveMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.saveMedicine(medicine)
            _saveSuccess.value = true
        }
    }
}
