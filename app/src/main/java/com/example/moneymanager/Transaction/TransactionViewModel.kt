package com.example.moneymanager.Transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionViewModel : ViewModel() {

    private val _navigationStatus = MutableLiveData<Boolean?>()

    val navigationStatus: MutableLiveData<Boolean?>
        get() = _navigationStatus


    fun navigationToAdding(){
        _navigationStatus.value = true
    }

    fun navigationComplete(){
        _navigationStatus.value = null
    }

}