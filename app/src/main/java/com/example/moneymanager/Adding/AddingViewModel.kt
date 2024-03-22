package com.example.moneymanager.Adding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneymanager.DB.DataBase

class AddingViewModel(application: Application): AndroidViewModel(application) {

    var database: DataBase = DataBase.getInstance(application)

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