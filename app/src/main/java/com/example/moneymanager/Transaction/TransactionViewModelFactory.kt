package com.example.moneymanager.Transaction

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moneymanager.DB.DAO

class TransactionViewModelFactory(
    private val dataSource: DAO,
    private val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(dataSource, app) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}