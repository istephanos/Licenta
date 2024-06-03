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

    const val DATA_RX = "00000001-0000-1000-8000-008025000000"
    const val DATA_TX = "00000002-0000-1000-8000-008025000000"
    const val CREDITS_RX = "00000003-0000-1000-8000-008025000000"
    const val CREDITS_TX = "00000004-0000-1000-8000-008025000000"

    const val PRINTER_WRITE = "00005501-D102-11E1-9B23-74F07D000000"

    val IGNORED_SCENARIOS = listOf("singleSampleAdjustment", "singleTestAccuracyCheck")

    const val serviciu1 = "00002a00-0000-1000-8000-00805f9b34fb"
    const val serviciu2 = ""
    const val serviciu3 = ""
    // UUID-urile serviciului și caracteristicii specifice pe care vrei să le utilizezi
    val SERVICE_UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb") // Exemplu de UUID pentru un serviciu
    val CHARACTERISTIC_UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb") // Exemplu de UUID pentru o caracteristică

}