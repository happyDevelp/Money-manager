package com.example.moneymanager.Adding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddingViewModel: ViewModel() {

    private val _navigationStatus = MutableLiveData<Boolean?>()

    val navigationStatus: LiveData<Boolean?>
        get() = _navigationStatus

    fun navigationToTransaction(){
        _navigationStatus.value = true
    }

    fun navigationComplete(){
        _navigationStatus.value = false
    }


}