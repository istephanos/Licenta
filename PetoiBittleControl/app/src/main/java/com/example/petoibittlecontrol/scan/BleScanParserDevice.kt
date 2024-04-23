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

        if (bleScanResult.bleDevice.name.orEmpty().startsWith("AR")) {
            sendDiscoveredDevice(
                getPrinterDevice(bleScanResult)
            )
        }

        if (discoveredScanResult.contains(HEX_DEVICE_FILTER)) {
            val discoveredDevice = getDiscoveredDevice(bleScanResult)
            sendDiscoveredDevice(discoveredDevice)
        }
    }

    private fun getPrinterDevice(bleScanResult: ScanResult) = BleResponseModel(
        true,
        DeviceModel(
            name = bleScanResult.scanRecord.deviceName.orEmpty().trim(),
            macAddress = bleScanResult.bleDevice.macAddress,
            partNumber = "",
            serialNumber = bleScanResult.scanRecord.deviceName.orEmpty().trim(),
            manufactureStatus = "",
            deviceStatus = DeviceStatus.AVAILABLE
        )
    )

    private fun getDiscoveredDevice(bleScanResult: ScanResult): BleResponseModel {
        val manufacturerData = getManufacturerData(bleScanResult)
        val partNumber = getPartNumber(manufacturerData)
        val serialNumber = getSerialNumber(manufacturerData)
        val deviceStatus = manufacturerData.slice(26..27)

        return BleResponseModel(
            true,
            DeviceModel(
                name = getDeviceName(bleScanResult),
                macAddress = bleScanResult.bleDevice.macAddress,
                partNumber = partNumber,
                serialNumber = serialNumber,
                manufactureStatus = deviceStatus,
                deviceStatus = DeviceStatus.AVAILABLE
            )
        )
    }

    private fun getDeviceName(bleScanResult: ScanResult) =
        bleScanResult.scanRecord.deviceName.orEmpty().trim().replace(Regex("\\p{Cntrl}"), "")

    private fun getManufacturerData(bleScanResult: ScanResult): String {
        return bleScanResult.scanRecord.manufacturerSpecificData
            .values()
            .firstOrNull()?.let {
                HEX_DEVICE_FILTER + it.toHex()
            } ?: bleScanResult.scanRecord.bytes.toHex()
    }

    private fun getSerialNumber(manufacturerData: String): String {
        var serialNumber = swapEndian(manufacturerData.slice(16..25)).toString(16).padStart(10, '0')

        val serialNumberPPJM = serialNumber.slice(0..5).toBigInteger(16).toString(36).padStart(4, '0')
        val serialNumberXXXX = serialNumber.slice(6..9).toBigInteger(16).toString(10).padStart(4, '0')
        serialNumber = "$serialNumberPPJM-$serialNumberXXXX"
        return serialNumber.uppercase(Locale.ROOT)
    }

    private fun getPartNumber(manufacturerData: String): String {
        var partNumber = manufacturerData.slice(8..15)
        val swapEndianResult = swapEndian(partNumber)
        val firstPart = floor((swapEndianResult.toInt() / 100000).toDouble()).toInt()
        val secondPart = floor((swapEndianResult.toInt() % 100000).toDouble()).toInt()
        partNumber = firstPart.toString(36).padStart(2, '0') + secondPart.toString(10).padStart(5, '0')
        return partNumber.uppercase(Locale.ROOT)
    }
}
