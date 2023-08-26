package com.udacity.shoestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.datasource.AccountDataSource
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel(private val accountDataSource: AccountDataSource) : ViewModel() {

    private var _ShoeList: MutableLiveData<ArrayList<Shoe>?> = MutableLiveData<ArrayList<Shoe>?>()
    val shoeList: LiveData<ArrayList<Shoe>?> = _ShoeList

    private var _SelectedShoeIndex: MutableLiveData<Int> = MutableLiveData<Int>()

    fun updateSelectedShoeIndex(index: Int) {
        _SelectedShoeIndex.value = index
    }

    fun getSelectedShoeIndex(): Int = _SelectedShoeIndex.value ?: -1

    fun getSelectedShoe(): Shoe {
        _ShoeList.value?.let {shoeList ->
            if (_SelectedShoeIndex.value != null && _SelectedShoeIndex.value != -1) {
                return shoeList[_SelectedShoeIndex.value!!]
            }
        }
        return Shoe()
    }

    fun updateShoeList(index: Int, shoe: Shoe) {
        shoe.let {
            _ShoeList.value?.set(index, it)
        }
    }

    fun addShoeList(shoe: Shoe) {
        shoe.let {
            _ShoeList.value?.add(shoe)
        }
    }

    fun setShoeList() {
        _ShoeList.value = dummyShoeList()
    }

    fun logout() {
        accountDataSource.removeAccount()
    }

    private fun dummyShoeList(): ArrayList<Shoe> = arrayListOf<Shoe>().apply {
        for (i in 1..10) {
            add(Shoe("Shoe $i", i.toString(), "Company $i", "description $i"))
        }
    }
}