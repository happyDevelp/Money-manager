package com.example.moneymanager.DI

import androidx.lifecycle.LiveData
import com.example.moneymanager.DB.DAO
import com.example.moneymanager.DB.TransactionEntity

class DatabaseRepository(private val dao: DAO) {
    fun insertTransaction(transaction: TransactionEntity) = dao.insertTransaction(transaction)

    fun getAllTransactions() = dao.getAllTransactions()

    fun getTransactionById(id: Int) = dao.getTransactionById(id)

    fun getTransactionsByType(type: String): List<TransactionEntity> = dao.getTransactionsByType(type)

    fun deleteAllTransactions() = dao.deleteAllTransactions()

    fun deleteTransactionById(id: Int) = dao.deleteTransactionById(id)

    fun getMonthSumByType(type: String, month: String, year: String): Int = dao.getMonthSumByType(type, month, year)

    fun changeFavState(newFavValue: Boolean, id: Int) = dao.changeFavState(newFavValue, id)

    fun getAllFavourites(): LiveData<List<TransactionEntity>> = dao.getAllFavourites()

    fun searchTransaction(query: String): List<TransactionEntity> = dao.searchTransaction(query)

    fun getTransactionsByMonth(month: String, yearNum: String): List<TransactionEntity> = dao.getTransactionByMonth(month, yearNum)

    fun updateTransaction(
        amount: Int,
        transactionType: String,
        transactionCategory: String,
        wallet: String,
        dateOfTransaction: String,
        comment: String?,
        imageUri: String?,
        id: Int) = dao.updateTransactionById(
        amount, transactionType, transactionCategory, wallet, dateOfTransaction, comment, imageUri, id)
}