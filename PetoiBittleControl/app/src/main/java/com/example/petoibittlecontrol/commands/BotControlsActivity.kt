package com.example.petoibittlecontrol.commands

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.BleConstants.CARACTERISTICA_TX
import com.example.petoibittlecontrol.BleConstants.SERVICIU_TX
import com.example.petoibittlecontrol.mainController.logs.LogActivity
import com.example.petoibittlecontrol.util.LogDatabaseHelper


class BotControlsActivity : AppCompatActivity() {

    private var bluetoothConnectionManager: BluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)
    private lateinit var forwardCommand: Button
    private lateinit var backwardCommand: Button
    private lateinit var restCommand: Button
    private lateinit var leftCommand: Button
    private lateinit var rightCommand: Button
    private lateinit var logsButton: Button
    private lateinit var allCommandButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bot_controls)

        title = "Trimitere comenzi catre robot"

        //butoane
        forwardCommand = findViewById(R.id.button_up)
        backwardCommand = findViewById(R.id.button_down)
        restCommand = findViewById(R.id.button_center)
        leftCommand = findViewById(R.id.button_left)
        rightCommand = findViewById(R.id.button_right)
        logsButton = findViewById(R.id.button_logs)
        allCommandButton = findViewById(R.id.button_all_commands)

        //actiuni butoane
        forwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.WALK_FORWARD)
            Log.d("Comanda robot", " Inainte ");
            saveLog("Comanda trimisa: Inainte")
        }

        backwardCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, BleCommands.BACKWARD)
            Log.d("Comanda robot", " Inapoi ");
            saveLog("Comanda trimisa: Inapoi")
        }

        restCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,
                BleCommands.REST)
            Log.d("Comanda robot", " Odihna ");
            saveLog("Comanda trimisa: Odihna")
        }

        leftCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,
                BleCommands.WALK_LEFT)
            Log.d("Comanda robot", " Stanga ");
            saveLog("Comanda trimisa: Stanga")
        }

        rightCommand.setOnClickListener{
            bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX,this,
                BleCommands.WALK_RIGHT)
            Log.d("Comanda robot", " Dreapta ");
            saveLog("Comanda trimisa: Dreapta")
        }

        logsButton.setOnClickListener{
            //open LogActivity
            val intent = Intent(this@BotControlsActivity, LogActivity::class.java)
            startActivity(intent)
        }

        allCommandButton.setOnClickListener{
            //open AllCommandsActivity
            val intent = Intent(this@BotControlsActivity, AllCommandsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveLog(command: String) {
        val dbHelper = LogDatabaseHelper(this)
        dbHelper.addLog(command)
    }
}