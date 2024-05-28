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


    suspend fun getSumByType(type: String): Int {
        return withContext(Dispatchers.IO) {repository.getSumByType(type)}
    }
    suspend fun getAllTransactions(): LiveData<List<TransactionEntity>> {
        return withContext(Dispatchers.IO) { repository.getAllTransactions() }
    }

    suspend fun getTransactionById(id: Int): TransactionEntity {
        return withContext(Dispatchers.IO) { repository.getTransactionById(id) }
    }

    suspend fun deleteAllTransactions() {
        withContext(Dispatchers.IO) { repository.deleteAllTransactions() }
    }

    fun navigationToAdding() { _navigationStatus.value = true }

    fun navigationComplete() { _navigationStatus.value = null }

}
