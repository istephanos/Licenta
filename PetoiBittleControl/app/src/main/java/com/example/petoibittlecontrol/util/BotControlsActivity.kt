package com.example.petoibittlecontrol.util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.scan.BleCommands
import com.example.petoibittlecontrol.scan.BleConstants.CARACTERISTICA_TX
import com.example.petoibittlecontrol.scan.BleConstants.SERVICIU_TX


class BotControlsActivity : AppCompatActivity() {

    private var bluetoothConnectionManager: BluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)
    private lateinit var forwardCommand: Button
    private lateinit var backwardCommand: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bot_controls)
        forwardCommand = findViewById(R.id.button_up)
        backwardCommand = findViewById(R.id.button_down)
        forwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.KWKF)
        }

        backwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.KBK)
        }


}
}