package com.example.moneymanager.DB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 3)
abstract class DataBase : RoomDatabase() { abstract val DAO: DAO }