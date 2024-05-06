package com.example.moneymanager.Adding

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.DB.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddingViewModel(application: Application): AndroidViewModel(application) {

    var database: DataBase = DataBase.getInstance(application)

    private val _navigationStatus = MutableLiveData<Boolean?>()

    var imageGalleryUri: Uri? = null

    val navigationStatus: LiveData<Boolean?>
        get() = _navigationStatus

    fun navigationToTransaction(){
        _navigationStatus.value = true
    }

    fun navigationComplete(){
        _navigationStatus.value = false
    }

    suspend fun pushTransaction(transaction: TransactionEntity) {
        return withContext(Dispatchers.IO) {
            database.DAO.insertTransaction(transaction)
        }
    }


}