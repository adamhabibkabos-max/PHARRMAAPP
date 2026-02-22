package com.pharmaconnect.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long,
    val status: String,
    val totalAmount: Double
)
