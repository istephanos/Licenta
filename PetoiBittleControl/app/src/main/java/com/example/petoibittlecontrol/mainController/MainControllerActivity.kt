package com.example.petoibittlecontrol.mainController

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.databinding.ActivityMainControllerBinding
import com.example.petoibittlecontrol.scan.model.DeviceModel
import com.example.petoibittlecontrol.scan.model.DeviceStatus
import com.example.petoibittlecontrol.util.BotControlsActivity
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainControllerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainControllerBinding
    private val viewModel: MainControllerViewModel by viewModel()
    private var scanDisposable: Disposable? = null
    private lateinit var bluetoothConnectionManager: BluetoothConnectionManager
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
            startBleScan()
        }
        bluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = DeviceAdapter(viewModel.listOfDevices.value?.toList() ?: emptyList(), clickListener = {
            connectToDevice(it)
        }, openRobot = {
            //open BotControlsActivity
            val intent = Intent(this, BotControlsActivity::class.java)
            startActivity(intent)
        })
        binding.scanResults.layoutManager = LinearLayoutManager(this)
        binding.scanResults.adapter = adapter


        viewModel.listOfDevices.observe(this) { devices ->
            val namedDevices = devices.filter { it.name.isNotEmpty() }
            adapter.updateDevices(namedDevices.toList())
        }
    }

    private fun updateButtonUIState() {
        binding.scanToggleBtn.setText(if (isScanning) R.string.stop_scan else R.string.start_scan)

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                binding.scanToggleBtn.setText(R.string.start_scan)
                Toast.makeText(this@MainControllerActivity, "Scanare finalizata ", Toast.LENGTH_SHORT).show()

            }
        }.start()
    }

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
                    startBleScan()
                    hasClickedScan = false
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
        scanDisposable = if (isScanning) {
            scanDisposable?.dispose()
            null
        } else {
            viewModel.bleScanManager.startScan()
        }
        updateButtonUIState()
    }

    private fun connectToDevice(device: DeviceModel) {
        Toast.makeText(this, "Connecting to ${device.name}", Toast.LENGTH_SHORT).show()

        bluetoothConnectionManager.connectToDevice(device.macAddress) { isConnected ->
            runOnUiThread {
                if (isConnected) {
                    Log.i("MainControllerActivity", "Connected to ${device.name}")
                    Toast.makeText(this, "Conectat cu succes la ${device.name}", Toast.LENGTH_SHORT).show()
                    viewModel.listOfDevices.observe(this) { devices ->
                        devices.first { it.macAddress == device.macAddress }.deviceStatus = DeviceStatus.CONNECTED
                    }
                } else {
                    Log.i("MainControllerActivity", "Disconnected from ${device.name}")
                    Toast.makeText(this, "Nu s-a putut realiza conectarea la ${device.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onScanFailure(throwable: Throwable) {
        Log.w("ScanActivity", "Scan failed", throwable)
    }

    private fun dispose() {
        scanDisposable = null
        updateButtonUIState()
    }
}
