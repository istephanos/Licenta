package com.example.petoibittlecontrol.scan

import java.util.UUID

object BleConstants {
    const val HEX_DEVICE_FILTER = "bc040c"
    const val REQUEST_PERMISSION_BLE_SCAN = 101
    const val FUTURE_DATE = 64060585200
    const val BLE_TIME_OUT = 17000L

    const val WRITE_CREDITS = "FF"
    const val RESULT_START = "1234"
    const val RESULT_END = "4321"

    const val SCENARIO_IGNORED_KEY = "lastDownload"
    const val LOGS_HEADER_KEY = "header"


    val IGNORED_SCENARIOS = listOf("singleSampleAdjustment", "singleTestAccuracyCheck")

    val SERVICIU_1: UUID = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb")
    val SERVICIU_2: UUID = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb")
    val SERVICIU_TX: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")


    val CARACTERISTICA_TX: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")
    val CARACTERISTICA_32: UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb")


}