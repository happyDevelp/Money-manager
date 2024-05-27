package com.example.moneymanager.PieChart

import androidx.lifecycle.ViewModel
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DI.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PieChartViewModel(private val repository: DatabaseRepository): ViewModel() {

    suspend fun getTransactionsByType(type: String): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            repository.getTransactionsByType(type)
        }
    }
}