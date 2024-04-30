package com.example.petoibittlecontrol.scan.model

import android.os.Parcelable


data class DeviceModel(

    var macAddress: String = "",
    var name: String = "",
    var partNumber: String = "",
    var type: String = "",
    var serialNumber: String = "",
    var manufactureStatus: String = "",
    var deviceStatus: DeviceStatus = DeviceStatus.DEFAULT,

)  {

    fun getSerialNoDisplayed() = serialNumber.replace("-","-")
}