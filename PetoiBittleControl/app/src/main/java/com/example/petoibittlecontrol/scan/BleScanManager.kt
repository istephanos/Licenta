package com.example.petoibittlecontrol.scan

import android.os.Handler
import android.os.Looper
import com.example.petoibittlecontrol.scan.model.BleResponseModel
import com.example.petoibittlecontrol.util.RxSchedulers
/*import com.draeger.add.data.bluetooth.scan.model.BleResponseModel
import com.draeger.add.domain.util.rx.RxBus
import com.draeger.add.domain.util.rx.RxSchedulers*/
import com.polidea.rxandroidble3.RxBleClient
import com.polidea.rxandroidble3.scan.ScanFilter
import com.polidea.rxandroidble3.scan.ScanResult
import com.polidea.rxandroidble3.scan.ScanSettings
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.module.Module
import org.koin.dsl.module



open class BleScanManager(
    private val bleRxBus: RxBus,
    private val rxBleClient: RxBleClient,
    private val rxSchedulers: RxSchedulers
) {

    private var isScanning = false
    private var scanDisposable: Disposable? = null
    private val bleParseDevice = BleScanParserDevice()
    private val scanDelayHandler = Handler(Looper.getMainLooper())

    fun startScan(scanDelay: Long = 500) {
        disposeScan()

        if (rxBleClient.isScanRuntimePermissionGranted) {
            discoverDevices(scanDelay)
        } else {
            initNoDevicesFound()
        }
    }

    fun disposeScan() {
        scanDelayHandler.removeCallbacksAndMessages(null)
        scanDisposable?.dispose()
        scanDisposable = null
        isScanning = false
    }

    fun isScanning() = isScanning

    private fun discoverDevices(scanDelay: Long) {
        scanDelayHandler.postDelayed({
            scanBleDevices()
                .observeOn(rxSchedulers.background())
                .subscribe({
                    isScanning = true
                    bleParseDevice.parseScanResult(it, ::sendDiscoveredDevice)
                }, {
                    onScanFailure(it)
                    initNoDevicesFound()
                })
                .let { scanDisposable = it }
        }, scanDelay)
    }

    private fun sendDiscoveredDevice(bleResponseModel: BleResponseModel) {
        bleRxBus.send(bleResponseModel)
    }

    private fun initNoDevicesFound() {
        bleRxBus.send(BleResponseModel(false))
    }

    private fun onScanFailure(throwable: Throwable) {
       // if (throwable is BleScanException) Timber.e(throwable)
    }

    private fun scanBleDevices(): Observable<ScanResult> {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
            .build()

        return rxBleClient.scanBleDevices(scanSettings, scanFilter)
    }
}