package com.example.petoibittlecontrol.connection

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.petoibittlecontrol.commands.BleCommands
import com.example.petoibittlecontrol.util.BluetoothPermissionsUtil
import java.util.UUID

class BluetoothConnectionManager(private val context: Context) {

    private var bluetoothGatt: BluetoothGatt? = null
    companion object {
        @Volatile
        private var INSTANCE: BluetoothConnectionManager? = null

        fun getInstance(context: Context): BluetoothConnectionManager {
            return INSTANCE ?: synchronized(this) {
                val instance = BluetoothConnectionManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }

    fun connectToDevice(macAddress: String, onConnectionStateChange: (Boolean) -> Unit) {

        try {
            val bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress)
            bluetoothGatt = bluetoothDevice.connectGatt(context, false, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    if (newState == BluetoothGatt.STATE_CONNECTED) {
                        Log.i("BluetoothGattCallback", "Successfully connected to $macAddress")
                        gatt.discoverServices()
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
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        onConnectionStateChange(true)

                        //returneaza CallBack: conectare cu succes


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

    fun writeCommand(serviciu: UUID, caracteristica: UUID, context: Context, command: BleCommands) {
        val service = bluetoothGatt?.getService(serviciu)
        service?.let {
            val characteristic = it.getCharacteristic(caracteristica)
            characteristic?.let { char ->
                // Abonare la notificări
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // Handle the missing permissions
                    return
                }
                bluetoothGatt?.setCharacteristicNotification(char, true)

                // Scriere valoare pe caracteristică
                char.value = command.byteArray
                bluetoothGatt?.writeCharacteristic(char)
            }
        }
    }

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
