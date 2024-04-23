package com.example.petoibittlecontrol.util

import android.app.Application
import com.polidea.rxandroidble3.RxBleClient
import com.polidea.rxandroidble3.LogConstants
import com.polidea.rxandroidble3.LogOptions

class SampleApp : Application() {
    companion object {
        lateinit var rxBleClient: RxBleClient
            private set
    }

    override fun onCreate() {
        super.onCreate()
        rxBleClient = RxBleClient.create(this)
        RxBleClient.updateLogOptions(LogOptions.Builder()
            .setLogLevel(LogConstants.INFO)
            .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
            .setUuidsLogSetting(LogConstants.UUIDS_FULL)
            .setShouldLogAttributeValues(true)
            .build()
        )
    }
}