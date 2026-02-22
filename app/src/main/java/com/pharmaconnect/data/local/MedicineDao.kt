package com.pharmaconnect.data.local

import androidx.room.*
import com.pharmaconnect.model.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines ORDER BY name ASC")
    fun getAllMedicines(): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchMedicines(query: String): Flow<List<Medicine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicine): Long

    @Update
    suspend fun update(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("SELECT COUNT(*) FROM medicines")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM medicines WHERE quantity <= :threshold")
    fun getLowStockCount(threshold: Int = 10): Flow<Int>

    @Query("SELECT COUNT(*) FROM medicines WHERE expiryDate <= :timeLimit")
    fun getExpiringSoonCount(timeLimit: Long): Flow<Int>

    @Query("SELECT * FROM medicines WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Medicine?
}
