package com.pharmaconnect.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val brand: String,
    val strength: String,
    val quantity: Int,
    val price: Double,
    val expiryDate: Long,
    val category: String?
)
