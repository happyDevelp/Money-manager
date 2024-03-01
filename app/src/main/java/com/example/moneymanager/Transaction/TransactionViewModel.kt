package com.example.moneymanager.Transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.tabs.TabLayoutMediator

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