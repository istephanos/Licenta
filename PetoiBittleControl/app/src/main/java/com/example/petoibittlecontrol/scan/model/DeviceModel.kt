package com.example.petoibittlecontrol.scan.model

import android.os.Parcelable


data class DeviceModel(

    var macAddress: String = "",
    var name: String = "",
    var serialNumber: String = "",
    var deviceStatus: DeviceStatus = DeviceStatus.DEFAULT,

)  {
}