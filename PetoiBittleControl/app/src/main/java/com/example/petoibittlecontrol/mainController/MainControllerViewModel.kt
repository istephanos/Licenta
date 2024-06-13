package com.example.petoibittlecontrol.mainController

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petoibittlecontrol.scan.BleScanManager
import com.example.petoibittlecontrol.scan.RxBus
import com.example.petoibittlecontrol.scan.model.BleResponseModel
import com.example.petoibittlecontrol.scan.model.DeviceModel
import com.example.petoibittlecontrol.util.RxSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class MainControllerViewModel(
    val bleScanManager : BleScanManager,
    val rxBus: RxBus,
    private val compositeDisposable: CompositeDisposable,
    private val rxSchedulers: RxSchedulers,
):ViewModel()
{

    private val _listOfDevices = MutableLiveData<MutableSet<DeviceModel>>(mutableSetOf())
    val listOfDevices: LiveData<MutableSet<DeviceModel>> get() = _listOfDevices
init {
    getDiscoveredDevice()
}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    private fun getDiscoveredDevice() {
        rxBus.toObservable()
            .observeOn(rxSchedulers.androidUI())
            .filter { it is BleResponseModel }
            .map { it as BleResponseModel }
            .subscribe({ bleResponseModel ->

                val devices = _listOfDevices.value ?: mutableSetOf()
                devices.add(bleResponseModel.discoveredDevices)
                _listOfDevices.postValue(devices)
                Log.d("MainControllerViewModel", "Discovered device: ${bleResponseModel.discoveredDevices.name}, MAC: ${bleResponseModel.discoveredDevices.macAddress}")

                Log.e("Devices", bleResponseModel.discoveredDevices.name)
            }, {
                Log.e("mda", it.localizedMessage)
            }).disposeBy(compositeDisposable)
    }


    fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}
