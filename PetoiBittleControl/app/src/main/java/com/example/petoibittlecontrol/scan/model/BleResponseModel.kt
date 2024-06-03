package com.example.petoibittlecontrol.scan.model

import android.bluetooth.BluetoothDevice

class BleResponseModel(
    var statusSuccess: Boolean,
    var discoveredDevices: DeviceModel = DeviceModel()
)