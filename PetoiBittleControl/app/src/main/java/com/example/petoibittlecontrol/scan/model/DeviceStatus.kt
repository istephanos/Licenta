package com.example.petoibittlecontrol.scan.model

import android.content.Context
import androidx.compose.material3.Text
import com.example.petoibittlecontrol.R

enum class DeviceStatus {
    DISABLE {
        override fun getDeviceStatusName(context: Context): String {
            return "DISABLE"
        }
    },
    DEFAULT {
        override fun getDeviceStatusName(context: Context): String {
            return "DEFAULT"
        }
    },
    AVAILABLE {
        override fun getDeviceStatusName(context: Context): String {
            return "Device available"
        }
    },
    NOT_AVAILABLE {
        override fun getDeviceStatusName(context: Context): String {
            return " DEVICE NOT_AVAILABLE"
        }
    },
    CONNECTED {
        override fun getDeviceStatusName(context: Context): String {
            return "DEVICE CONNECTED"
        }
    },
    NOT_CONNECTED {
        override fun getDeviceStatusName(context: Context): String {
            return "DEVICE NOT CONNECTED"
        // return context.getString(R.string.text_device_not_connected)
        }
    };

    abstract fun getDeviceStatusName(context: Context):String
}