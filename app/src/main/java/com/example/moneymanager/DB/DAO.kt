package com.example.moneymanager.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTransaction(transaction: TransactionEntity)

    @Query("select * from transaction_table")
     fun getAllTransactions(): LiveData<List<TransactionEntity>>

    @Query("select * from transaction_table where id = :id")
    fun getTransactionById(id: Int): TransactionEntity

    @Query("select * from transaction_table where transaction_type = :type")
    fun getTransactionsByType(type: String): List<TransactionEntity> //return income or spent

     @Query("delete from transaction_table")
     fun deleteAllTransactions()

     @Query("delete from transaction_table where id = :id")
     fun deleteTransactionById(id: Int)

     @Query("select SUM(amount) from transaction_table where transaction_type =:type")
     fun getSumByType(type: String): Int

    @Query("update transaction_table set is_fav =:newFavValue where id =:id")
    fun changeFavState(newFavValue: Boolean, id: Int)

    @Query("select * from transaction_table where is_fav = 1")
    fun getAllFavourites(): LiveData<List<TransactionEntity>>

     @Query("update transaction_table set amount=:amount, transaction_type=:transactionType," +
             " transaction_category=:transactionCategory, wallet=:wallet, date_of_transaction=:dateOfTransaction," +
             " comment=:comment, image_uri=:imageUri where id=:id")
     fun updateTransactionById(
         amount: Int,
         transactionType: String,
         transactionCategory: String,
         wallet: String,
         dateOfTransaction: String,
         comment: String?,
         imageUri: String?,
         id: Int
     )

}