package com.udacity.shoestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import com.udacity.shoestore.datasource.AccountDataSource
import com.udacity.shoestore.datasource.AccountDataSourceImp

class MainActivity : AppCompatActivity () {

    private lateinit var binding: ActivityMainBinding
    private lateinit var accountDataSource: AccountDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        accountDataSource = AccountDataSourceImp(this)

        setSupportActionBar(binding.toolbar)

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        if (accountDataSource.getAccount().isNotEmpty()) {
            val action = LoginFragmentDirections.actionLoginScreenToShoeListScreen()
            navController.navigate(action)
        }

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
