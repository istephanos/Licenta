package com.example.petoibittlecontrol.scan.model

import android.content.Context
import androidx.compose.material3.Text
import com.example.petoibittlecontrol.R

enum class DeviceStatus {
    DISABLE {
        override fun getDeviceStatusName(context: Context): String {
            return "DISABLE"
        }
        override var buttontDescription: String
            get() = "NOT CONNECTED"
            set(value) {}
    },
    DEFAULT {
        override fun getDeviceStatusName(context: Context): String {
            return "DEFAULT"
        }
        override var buttontDescription: String
            get() = "Pair"
            set(value) {}
    },
    AVAILABLE {
        override fun getDeviceStatusName(context: Context): String {
            return "Device available"
        }
        override var buttontDescription: String
            get() = "AVAILABLE"
            set(value) {}
    },
    NOT_AVAILABLE {
        override fun getDeviceStatusName(context: Context): String {
            return " DEVICE NOT_AVAILABLE"
        }
        override var buttontDescription: String
            get() = "NOT CONNECTED"
            set(value) {}
    },
    CONNECTED {
        override fun getDeviceStatusName(context: Context): String {
            return "DEVICE CONNECTED"
        }
        override var buttontDescription: String
            get() = "CONNECTED"
            set(value) {}
    },
    NOT_CONNECTED {
        override fun getDeviceStatusName(context: Context): String {
            return "DEVICE NOT CONNECTED"
        // return context.getString(R.string.text_device_not_connected)
        }

        override var buttontDescription: String
            get() = "NOT CONNECTED"
            set(value) {}
    };

    abstract fun getDeviceStatusName(context: Context):String
    abstract var buttontDescription: String

}