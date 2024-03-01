package com.example.moneymanager.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "transaction_type") val transactionType: String, // salary, help, gifts, other

    @ColumnInfo(name = "amount") val amount: Int, // Transaction amount

    @ColumnInfo(name = "wallet") val wallet: String, // choosing a wallet

    @ColumnInfo(name = "date_of_transaction") val dateOfTransaction: String,

    @ColumnInfo(name = "comment") val comment: String?
)