package com.example.moneymanager.DI

import android.content.Context
import androidx.room.Room
import com.example.moneymanager.DB.DataBase

fun provideDatabase(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    DataBase::class.java,
    "database"
)
    .fallbackToDestructiveMigration()
    .build()

fun provideDao(db: DataBase) = db.DAO