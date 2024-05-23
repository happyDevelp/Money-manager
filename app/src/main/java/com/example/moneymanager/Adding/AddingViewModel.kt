package com.example.moneymanager.Adding

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DI.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddingViewModel(private val repository: DatabaseRepository): ViewModel() {

    private val _navigationStatus = MutableLiveData<Boolean?>()

    var imageGalleryUri: Uri? = null

    val navigationStatus: LiveData<Boolean?>
        get() = _navigationStatus

    fun navigationToTransaction(){ _navigationStatus.value = true }

    fun navigationComplete(){ _navigationStatus.value = false }

    suspend fun pushTransaction(transaction: TransactionEntity) {
        return withContext(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }
    }

    suspend fun getTransactionById(id: Int): TransactionEntity {
        return withContext(Dispatchers.IO) {
            repository.getTransactionById(id)
        }
    }

    suspend fun updateTransaction(    amount: Int,
                                      transactionType: String,
                                      transactionCategory: String,
                                      wallet: String,
                                      dateOfTransaction: String,
                                      comment: String?,
                                      imageUri: String?,
                                      id: Int) {
        withContext(Dispatchers.IO) {
            repository.updateTransaction(amount, transactionType, transactionCategory, wallet, dateOfTransaction, comment, imageUri, id)
        }
    }

}