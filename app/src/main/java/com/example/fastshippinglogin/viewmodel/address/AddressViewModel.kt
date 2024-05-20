package com.example.fastshippinglogin.viewmodel.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewModel : ViewModel() {

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> = _longitude

    fun setAddress(newAddress: String) {
        _address.value = newAddress
    }

    fun setLatitude(newLatitude: Double) {
        _latitude.value = newLatitude
    }

    fun setLongitude(newLongitude: Double) {
        _longitude.value = newLongitude
    }
}
