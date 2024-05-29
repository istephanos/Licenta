package com.example.petoibittlecontrol.mainController

import android.util.Log
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

    val listOfDevices = mutableSetOf<DeviceModel>()
init {
    getDiscoveredDevice()
}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
    fun getDiscoveredDevice() {
        rxBus.toObservable()
            .observeOn(rxSchedulers.androidUI())
            .filter { it is BleResponseModel }
            .map { it as BleResponseModel }
            .subscribe({ bleResponseModel ->

                       //device descoperit
                listOfDevices.add(bleResponseModel.discoveredDevices)
                Log.e("Devices", bleResponseModel.discoveredDevices.name)
            }, {
                Log.e("mda", it.localizedMessage)
            }).disposeBy(compositeDisposable)
    }


    fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}
