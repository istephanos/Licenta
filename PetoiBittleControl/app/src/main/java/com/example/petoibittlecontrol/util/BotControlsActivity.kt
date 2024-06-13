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
    private lateinit var restCommand: Button
    private lateinit var leftCommand: Button
    private lateinit var rightCommand: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bot_controls)

        //butoane
        forwardCommand = findViewById(R.id.button_up)
        backwardCommand = findViewById(R.id.button_down)
        restCommand = findViewById(R.id.button_center)
        leftCommand = findViewById(R.id.button_left)
        rightCommand = findViewById(R.id.button_right)

        //actiuni butoane
        forwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.KWKF)
        }

        backwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.KBK)
        }

        restCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,BleCommands.KREST)
        }

        leftCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,BleCommands.KWKL)
        }

        rightCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,BleCommands.KWKR)
        }
    }
}