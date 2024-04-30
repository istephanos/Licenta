package com.example.petoibittlecontrol.scan.model

class BleResponseModel(
    var statusSuccess: Boolean,
    var discoveredDevices: DeviceModel = DeviceModel()
)