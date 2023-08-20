package com.udacity.shoestore.datasource

interface AccountDataSource {
    fun getAccount(): String
    fun saveAccount(account: String)
    fun removeAccount()
}