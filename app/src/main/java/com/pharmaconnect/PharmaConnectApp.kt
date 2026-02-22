package com.pharmaconnect

import android.app.Application
import com.pharmaconnect.data.local.AppDatabase
import com.pharmaconnect.repository.PharmacyRepository

class PharmaConnectApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: PharmacyRepository by lazy { PharmacyRepository(database.medicineDao(), database.orderDao()) }
}
