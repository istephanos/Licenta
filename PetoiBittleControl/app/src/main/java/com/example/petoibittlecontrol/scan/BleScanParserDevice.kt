package com.example.petoibittlecontrol.scan

import com.example.petoibittlecontrol.BleConstants.HEX_DEVICE_FILTER
import com.example.petoibittlecontrol.scan.model.BleResponseModel

import com.example.petoibittlecontrol.scan.model.DeviceModel
import com.example.petoibittlecontrol.scan.model.DeviceStatus
import com.example.petoibittlecontrol.scan.model.toHex
import com.polidea.rxandroidble3.scan.ScanResult


class BleScanParserDevice {


    fun parseScanResult(bleScanResult: ScanResult, sendDiscoveredDevice: (bleResponseModel: BleResponseModel) -> Unit) {

        if (bleScanResult.bleDevice.name.orEmpty().startsWith("Bittle")
            || bleScanResult.bleDevice.name.orEmpty().startsWith("Petoi") ) {
            val discoveredDevice = getDiscoveredDevice(bleScanResult)
            sendDiscoveredDevice(discoveredDevice)
        }

        /*val discoveredDevice = getDiscoveredDevice(bleScanResult)
        sendDiscoveredDevice(discoveredDevice)*/
    }


    private fun getDiscoveredDevice(bleScanResult: ScanResult): BleResponseModel {
        val serialNumber = ""

        return BleResponseModel(
            true,
            DeviceModel(
                name = getDeviceName(bleScanResult),
                macAddress = bleScanResult.bleDevice.macAddress,
                serialNumber = serialNumber,
                deviceStatus = DeviceStatus.AVAILABLE
            )
        )
    }

    private fun getDeviceName(bleScanResult: ScanResult) =
        bleScanResult.bleDevice.name.orEmpty().trim().replace(Regex("\\p{Cntrl}"), "")

}
