package com.example.petoibittlecontrol.scan

import com.example.petoibittlecontrol.scan.model.BleResponseModel

import com.example.petoibittlecontrol.scan.BleConstants.HEX_DEVICE_FILTER
import com.example.petoibittlecontrol.scan.model.DeviceModel
import com.example.petoibittlecontrol.scan.model.DeviceStatus
import com.example.petoibittlecontrol.scan.model.swapEndian
import com.example.petoibittlecontrol.scan.model.toHex
import com.example.petoibittlecontrol.scan.model.values
import com.polidea.rxandroidble3.scan.ScanResult
import java.util.*
import kotlin.math.floor


class BleScanParserDevice {


    fun parseScanResult(bleScanResult: ScanResult, sendDiscoveredDevice: (bleResponseModel: BleResponseModel) -> Unit) {
        val discoveredScanResult = bleScanResult.scanRecord.bytes.toHex()

//        if (bleScanResult.bleDevice.name.orEmpty().startsWith("Pepa")) {
//            sendDiscoveredDevice(
//
//            )
//        }

//        if (discoveredScanResult.contains(HEX_DEVICE_FILTER)) {
//            val discoveredDevice = getDiscoveredDevice(bleScanResult)
//            sendDiscoveredDevice(discoveredDevice)
//        }

            val discoveredDevice = getDiscoveredDevice(bleScanResult)
            sendDiscoveredDevice(discoveredDevice)
    }

    private fun getDiscoveredDevice(bleScanResult: ScanResult): BleResponseModel {
        val manufacturerData = ""
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
        bleScanResult.scanRecord.deviceName.orEmpty().trim().replace(Regex("\\p{Cntrl}"), "")

}
