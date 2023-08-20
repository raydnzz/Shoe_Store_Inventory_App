package com.udacity.shoestore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.datasource.AccountDataSource

class ShoeListViewModelFactory(private val accountDataSource: AccountDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoeListViewModel::class.java)) {
            return ShoeListViewModel(accountDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
