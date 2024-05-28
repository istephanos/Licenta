package com.example.petoibittlecontrol.mainController

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.petoibittlecontrol.DeviceActivity
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.databinding.ActivityMainControllerBinding
import com.example.petoibittlecontrol.util.isScanPermissionGranted
import com.polidea.rxandroidble3.scan.ScanResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainControllerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainControllerBinding
    private val viewModel: MainControllerViewModel by viewModel()
    private var scanDisposable: Disposable? = null

    private var hasClickedScan = false

    companion object {
        const val REQUEST_BLUETOOTH_PERMISSIONS = 1
    }

    private val isScanning: Boolean
        get() = scanDisposable != null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        binding.scanToggleBtn.setOnClickListener {
            hasClickedScan = true
            checkAndRequestBluetoothPermissions()
        }
        checkAndRequestBluetoothPermissions()
    }

    private fun updateButtonUIState() =
        binding.scanToggleBtn.setText(if (isScanning) R.string.stop_scan else R.string.start_scan)

    private fun checkAndRequestBluetoothPermissions() {
        val permissions = mutableListOf(
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(android.Manifest.permission.BLUETOOTH_SCAN)
            permissions.add(android.Manifest.permission.BLUETOOTH_CONNECT)
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_BLUETOOTH_PERMISSIONS
            )
        } else {
            startBleScan()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                if (hasClickedScan) {
                    hasClickedScan = false
                    startBleScan()
                }
            } else {
                Log.w("MainControllerActivity", "Permissions not granted")
            }
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_controller)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun startBleScan() {
        if (isScanning) {
            scanDisposable?.dispose()
        } else {
            viewModel.bleScanManager.startScan()
        }
        updateButtonUIState()
    }

    private fun onScanFailure(throwable: Throwable) {
        Log.w("ScanActivity", "Scan failed", throwable)
    }

    private fun dispose() {
        scanDisposable = null
        updateButtonUIState()
    }
}
