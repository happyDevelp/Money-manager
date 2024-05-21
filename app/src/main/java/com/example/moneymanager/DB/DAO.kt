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

     @Query("delete from transaction_table")
     fun deleteAllTransactions()

     @Query("delete from transaction_table where id = :id")
     fun deleteTransactionById(id: Int)

}