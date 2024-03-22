package com.example.moneymanager.Transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.DB.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val database = DataBase.getInstance(application)


    private val _navigationStatus = MutableLiveData<Boolean?>()

    val navigationStatus: MutableLiveData<Boolean?>
        get() = _navigationStatus

    suspend fun getAllTransactions(): LiveData<List<TransactionEntity>> {
        return withContext(Dispatchers.IO) {
            database.DAO.getAllTransactions()
        }
    }


    suspend fun pushTransaction(transaction: TransactionEntity) {
        return withContext(Dispatchers.IO) {
            database.DAO.insertTransaction(transaction)
        }
    }




    fun navigationToAdding() {
        _navigationStatus.value = true
    }

    fun navigationComplete() {
        _navigationStatus.value = null
    }

}