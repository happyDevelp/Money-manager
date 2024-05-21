package com.example.moneymanager.DetailsFragment

import androidx.lifecycle.ViewModel
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DI.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailsViewModel(private val repository: DatabaseRepository) : ViewModel() {

    suspend fun getTransactionById(id: Int): TransactionEntity {
        return withContext(Dispatchers.IO) {
            repository.getTransactionById(id)
        }
    }

    suspend fun deleteTransactionById(id: Int) {
         withContext(Dispatchers.IO) {
            repository.deleteTransactionById(id)
        }
    }

}