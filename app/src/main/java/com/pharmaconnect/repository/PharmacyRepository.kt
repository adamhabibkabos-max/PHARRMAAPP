package com.pharmaconnect.repository

import com.pharmaconnect.data.local.MedicineDao
import com.pharmaconnect.data.local.OrderDao
import com.pharmaconnect.model.Medicine
import com.pharmaconnect.model.Order
import kotlinx.coroutines.flow.Flow

class PharmacyRepository(
    private val medicineDao: MedicineDao,
    private val orderDao: OrderDao
) {
    fun getAllMedicines(): Flow<List<Medicine>> = medicineDao.getAllMedicines()
    fun searchMedicines(query: String): Flow<List<Medicine>> = medicineDao.searchMedicines(query)
    suspend fun saveMedicine(medicine: Medicine) {
        if (medicine.id == 0L) medicineDao.insert(medicine) else medicineDao.update(medicine)
    }

    suspend fun getMedicineById(id: Long): Medicine? = medicineDao.getById(id)

    fun getAllOrders(): Flow<List<Order>> = orderDao.getAllOrders()
    fun getOrdersByDateRange(start: Long, end: Long): Flow<List<Order>> = orderDao.getOrdersByDateRange(start, end)
    suspend fun addOrder(order: Order) = orderDao.insert(order)

    fun getTotalMedicinesCount() = medicineDao.getTotalCount()
    fun getLowStockCount(threshold: Int = 10) = medicineDao.getLowStockCount(threshold)
    fun getExpiringSoonCount(timeLimit: Long) = medicineDao.getExpiringSoonCount(timeLimit)
    fun getTotalOrdersCount() = orderDao.getTotalOrderCount()

    fun getCompletedCount(start: Long, end: Long) = orderDao.getOrdersCountByStatus("Completed", start, end)
    fun getFailedCount(start: Long, end: Long) = orderDao.getOrdersCountByStatus("Failed", start, end)
    fun getRevenue(start: Long, end: Long) = orderDao.getRevenue(start, end)
}
