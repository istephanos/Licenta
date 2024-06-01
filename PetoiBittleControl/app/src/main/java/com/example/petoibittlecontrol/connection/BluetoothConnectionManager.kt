package com.example.petoibittlecontrol.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.content.Context
import android.util.Log
import com.example.petoibittlecontrol.util.BluetoothPermissionsUtil

class BluetoothConnectionManager(private val context: Context) {

    private var bluetoothGatt: BluetoothGatt? = null

    @SuppressLint("MissingPermission")
    fun connectToDevice(macAddress: String, onConnectionStateChange: (Boolean) -> Unit) {
        if (!BluetoothPermissionsUtil.hasBluetoothPermissions(context)) {
            Log.w("BluetoothConnectionManager", "Missing Bluetooth permissions")
            onConnectionStateChange(false)
            return
        }

        try {
            val bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress)
            bluetoothGatt = bluetoothDevice.connectGatt(context, false, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    if (newState == BluetoothGatt.STATE_CONNECTED) {
                        Log.i("BluetoothGattCallback", "Successfully connected to $macAddress")
                        gatt.discoverServices()
                        onConnectionStateChange(true)
                    } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                        Log.i("BluetoothGattCallback", "Disconnected from $macAddress")
                        onConnectionStateChange(false)
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        val services = gatt.services
                        for (service in services) {
                            Log.i("BluetoothGattCallback", "Discovered service: ${service.uuid}")
                            val characteristics = service.characteristics
                            for (characteristic in characteristics) {
                                Log.i("BluetoothGattCallback", "Discovered characteristic: ${characteristic.uuid}")
                            }
                        }
                    } else {
                        Log.w("BluetoothGattCallback", "onServicesDiscovered received: $status")
                    }
                }
            })
        } catch (e: SecurityException) {
            Log.e("BluetoothConnectionManager", "SecurityException: ${e.message}")
            onConnectionStateChange(false)
        } catch (e: Exception) {
            Log.e("BluetoothConnectionManager", "Exception: ${e.message}")
            onConnectionStateChange(false)
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        if (!BluetoothPermissionsUtil.hasBluetoothPermissions(context)) {
            Log.w("BluetoothConnectionManager", "Missing Bluetooth permissions")
            return
        }

        try {
            bluetoothGatt?.disconnect()
            bluetoothGatt?.close()
            bluetoothGatt = null
        } catch (e: SecurityException) {
            Log.e("BluetoothConnectionManager", "SecurityException: ${e.message}")
        } catch (e: Exception) {
            Log.e("BluetoothConnectionManager", "Exception: ${e.message}")
        }
    }
}
