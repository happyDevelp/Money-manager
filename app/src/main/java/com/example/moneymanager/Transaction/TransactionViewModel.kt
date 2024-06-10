package com.example.moneymanager.Transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DI.DatabaseRepository
import com.example.moneymanager.Utils.toMutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionViewModel(private val repository: DatabaseRepository) : ViewModel() {

    private val _transactions: MutableLiveData<List<TransactionEntity>> = repository.getAllTransactions().toMutableLiveData()
    val transactions: LiveData<List<TransactionEntity>>
        get() = _transactions


    private val _navigationStatus = MutableLiveData<Boolean?>()

    val navigationStatus = _navigationStatus

    suspend fun getSumByType(type: String, month: String, year: String): Int {
        return withContext(Dispatchers.IO) {repository.getMonthSumByType(type, month, year)}
    }

    suspend fun searchTransaction(query: String): List<TransactionEntity> {
        return withContext(Dispatchers.IO) { repository.searchTransaction(query) }
    }

    suspend fun getTransactionsByMonth(month: String, yearNum: String): List<TransactionEntity> {
        return withContext(Dispatchers.IO) {
            repository.getTransactionsByMonth(month, yearNum)
        }
    }

    fun navigationToAdding() { _navigationStatus.value = true }

    fun navigationComplete() { _navigationStatus.value = null }

}
