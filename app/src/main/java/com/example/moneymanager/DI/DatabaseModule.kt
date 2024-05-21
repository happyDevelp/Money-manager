package com.example.moneymanager.DI

import com.example.moneymanager.Adding.AddingViewModel
import com.example.moneymanager.DB.DAO
import com.example.moneymanager.DB.DataBase
import com.example.moneymanager.DetailsFragment.DetailsViewModel
import com.example.moneymanager.Transaction.TransactionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataBaseModule = module {
    single<DataBase> { provideDatabase(context = androidContext()) }
    single<DAO> { provideDao(db = get()) }
    //factory mean that it will be create new instance of DatabaseRepository class every time when it requested
    factory<DatabaseRepository> { DatabaseRepository(dao = get()) }

    viewModel {TransactionViewModel(repository = get())}
    viewModel {AddingViewModel(repository = get())}
    viewModel {DetailsViewModel(repository = get())}
}