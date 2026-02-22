package com.pharmaconnect.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pharmaconnect.model.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getAllOrders(): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order): Long

    @Query("SELECT * FROM orders WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getOrdersByDateRange(startDate: Long, endDate: Long): Flow<List<Order>>

    @Query("SELECT COUNT(*) FROM orders")
    fun getTotalOrderCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM orders WHERE status = :status AND date BETWEEN :startDate AND :endDate")
    fun getOrdersCountByStatus(status: String, startDate: Long, endDate: Long): Flow<Int>

    @Query("SELECT COALESCE(SUM(totalAmount), 0) FROM orders WHERE status = 'Completed' AND date BETWEEN :startDate AND :endDate")
    fun getRevenue(startDate: Long, endDate: Long): Flow<Double>
}
