package com.example.petoibittlecontrol.mainController

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.petoibittlecontrol.DeviceActivity
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.databinding.ActivityMainControllerBinding
import com.example.petoibittlecontrol.scan.BleScanManager
import com.example.petoibittlecontrol.scan.ScanResultsAdapter
import com.example.petoibittlecontrol.util.SampleApp
import com.example.petoibittlecontrol.util.isScanPermissionGranted
import com.example.petoibittlecontrol.util.requestScanPermission
import com.polidea.rxandroidble3.LogConstants
import com.polidea.rxandroidble3.LogOptions
import com.polidea.rxandroidble3.RxBleClient
import com.polidea.rxandroidble3.scan.ScanFilter
import com.polidea.rxandroidble3.scan.ScanResult
import com.polidea.rxandroidble3.scan.ScanSettings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainControllerActivity : AppCompatActivity() {

    //private val rxBleClient = SampleApp.rxBleClient
    private lateinit var rxBleClient : RxBleClient
    private lateinit var binding: ActivityMainControllerBinding
    private val viewModel : MainControllerViewModel by inject()

    private var bleDevices :List<ScanResult> = listOf()


    private var scanDisposable: Disposable? = null

    private val resultsAdapter =
        ScanResultsAdapter.ScanResultsAdapter {
            startActivity(
                DeviceActivity.newInstance(
                    this,
                    it.bleDevice.macAddress
                )
            )
        }
    private var hasClickedScan = false

    private val isScanning: Boolean
        get() = scanDisposable != null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxBleClient = RxBleClient.create(this)
        initBinding()
        binding.scanToggleBtn.setOnClickListener { onScanToggleClick() }
    }

    private fun updateButtonUIState() =
        binding.scanToggleBtn.setText(if (isScanning) R.string.stop_scan else R.string.start_scan)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (isScanPermissionGranted(requestCode, grantResults) && hasClickedScan) {
            hasClickedScan = false
            scanBleDevices()
        }
    }

    private fun initBinding(){
        viewModel.bleScanManager.startScan()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_controller)
        //binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun onScanToggleClick() {
        if (isScanning) {
            scanDisposable?.dispose()
        } else {
            if (rxBleClient.isScanRuntimePermissionGranted) {
                scanBleDevices()
            } else {
                hasClickedScan = true
                requestScanPermission(rxBleClient)
            }
        }
        updateButtonUIState()
    }

    private fun scanBleDevices() {
        //bleDevices = listOf()
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
//            .setDeviceAddress("B4:99:4C:34:DC:8B")
            // add custom filters if needed
            .build()

        rxBleClient.scanBleDevices(scanSettings, scanFilter)
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { dispose() }
            .subscribe({ device ->
                if (!bleDevices.any { it.bleDevice.macAddress == device.bleDevice.macAddress }) {
                    // Adăugăm dispozitivul în listă
                    bleDevices += device
                    Log.w("Devices:","New device found: ${device.bleDevice.name}")
                    // Aici puteți face orice altceva cu dispozitivul nou adăugat
                }

                //resultsAdapter.addScanResult(it)
                    }, { onScanFailure(it) })

            .let {
                scanDisposable = it }

        val scanSubscription = rxBleClient.scanBleDevices(
            ScanSettings.Builder() // .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // change if needed
                // .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES) // change if needed
                .build() // add filters if needed
        )
            .subscribe(
                { scanResult -> }
            ) { throwable -> }

// When done, just dispose.

// When done, just dispose.
        scanSubscription.dispose()
    }

    private fun onScanFailure(throwable: Throwable) {
        Log.w("ScanActivity", "Scan failed", throwable)
    }



    private fun dispose() {
        scanDisposable = null
        //resultsAdapter.clearScanResults()
        updateButtonUIState()
    }

}