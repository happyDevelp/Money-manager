package com.example.moneymanager.Transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.DB.DAO
import com.example.moneymanager.DB.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(private val database: DAO, application: Application) : AndroidViewModel(application) {

    private val _transactions = MutableLiveData<List<TransactionEntity>?>()
    val transactions: LiveData<List<TransactionEntity>?>
        get() = _transactions

    fun fetchEntities() {
        viewModelScope.launch {
            _transactions.value = withContext(Dispatchers.IO) {
                database.getAllTransactions().value
            }
        }
    }

    init {
        fetchEntities()
    }

    private val _navigationStatus = MutableLiveData<Boolean?>()

    val navigationStatus = _navigationStatus



    suspend fun getAllTransactions(): LiveData<List<TransactionEntity>> {
        return withContext(Dispatchers.IO) {
            database.getAllTransactions()
        }
    }

    suspend fun deleteAllTransactions() {
        withContext(Dispatchers.IO) {
            database.deleteAllTransactions()
        }
    }


    fun navigationToAdding() {
        _navigationStatus.value = true
    }

    fun navigationComplete() {
        _navigationStatus.value = null
    }

}