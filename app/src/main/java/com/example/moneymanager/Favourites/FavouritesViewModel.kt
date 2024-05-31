package com.example.moneymanager.Favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moneymanager.DB.TransactionEntity
import com.example.moneymanager.DI.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouritesViewModel(private val repository: DatabaseRepository) : ViewModel() {

    val favList:LiveData<List<TransactionEntity>> = repository.getAllFavourites()

    suspend fun changeFavState(newFavValue: Boolean, id: Int) {
        return withContext(Dispatchers.IO) {
            repository.changeFavState(newFavValue, id)
        }
    }
}