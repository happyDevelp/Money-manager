package com.example.moneymanager.Settings

import androidx.lifecycle.ViewModel
import com.example.moneymanager.DI.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsViewModel(private val repository: DatabaseRepository):ViewModel() {

    suspend fun deleteAllTransactions() {
        withContext(Dispatchers.IO) {
            repository.deleteAllTransactions()
        }
    }
}