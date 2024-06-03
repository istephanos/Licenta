package com.example.petoibittlecontrol.util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.scan.BleConstants
import com.example.petoibittlecontrol.scan.BleConstants.CHARACTERISTIC_UUID
import com.example.petoibittlecontrol.scan.BleConstants.SERVICE_UUID
import com.example.petoibittlecontrol.scan.BleConstants.serviciu1

class BotControlsActivity : AppCompatActivity() {

    private var bluetoothConnectionManager: BluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)
    private lateinit var upCommand: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bot_controls)
        upCommand = findViewById(R.id.button_up)
        upCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICE_UUID, CHARACTERISTIC_UUID,this)
        }


}}