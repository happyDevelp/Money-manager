package com.example.moneymanager.DI

import com.example.moneymanager.DB.DAO
import com.example.moneymanager.DB.TransactionEntity

class DatabaseRepository(private val dao: DAO) {
    fun insertTransaction(transaction: TransactionEntity) = dao.insertTransaction(transaction)

    fun getAllTransactions() = dao.getAllTransactions()

    fun getTransactionById(id: Int) = dao.getTransactionById(id)

    fun deleteAllTransactions() = dao.deleteAllTransactions()

    fun deleteTransactionById(id: Int) = dao.deleteTransactionById(id)

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