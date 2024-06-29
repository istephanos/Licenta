package com.example.petoibittlecontrol.commands

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.BleConstants.CARACTERISTICA_TX
import com.example.petoibittlecontrol.BleConstants.SERVICIU_TX
import com.example.petoibittlecontrol.mainController.logs.LogActivity
import com.example.petoibittlecontrol.util.LogDatabaseHelper
import java.util.Locale

class BotControlsActivity : AppCompatActivity() {

    private var bluetoothConnectionManager: BluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)
    private lateinit var forwardCommand: Button
    private lateinit var backwardCommand: Button
    private lateinit var restCommand: Button
    private lateinit var leftCommand: Button
    private lateinit var rightCommand: Button
    private lateinit var logsButton: Button
    private lateinit var allCommandButton: Button
    private lateinit var gestureButton: Button
    private lateinit var voiceCommandButton: Button

    private lateinit var voiceCommandLauncher: ActivityResultLauncher<Intent>

    private lateinit var gestureValuesText: TextView

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var originalColor: Int = 0
    private var highlightColor: Int = 0

    private var isGestureControl: Boolean = false

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val x = it.values[0]
                val y = it.values[1]

                gestureValuesText.text = """
                                        Inainte: y > 2
                                        Inapoi: y < -2
                                        Stanga: x > 2
                                        Dreapta: x < -2
                                        x: $x, y: $y
                                        """.trimIndent()

                if (x > 2) {
                    sendCommandToRobot(BleCommands.WALK_LEFT)
                    saveLog("Comanda trimisa: Stanga")
                } else if (x < -2) {
                    sendCommandToRobot(BleCommands.WALK_RIGHT)
                    saveLog("Comanda trimisa: Dreapta")
                }

                if (y > 2) {
                    sendCommandToRobot(BleCommands.WALK_FORWARD)
                    saveLog("Comanda trimisa: Inainte")
                } else if (y < -2) {
                    sendCommandToRobot(BleCommands.BACKWARD)
                    saveLog("Comanda trimisa: Inapoi")
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bot_controls)

        title = "Trimitere comenzi catre robot"

        forwardCommand = findViewById(R.id.button_up)
        backwardCommand = findViewById(R.id.button_down)
        restCommand = findViewById(R.id.button_center)
        leftCommand = findViewById(R.id.button_left)
        rightCommand = findViewById(R.id.button_right)
        logsButton = findViewById(R.id.button_logs)
        allCommandButton = findViewById(R.id.button_all_commands)
        gestureButton = findViewById(R.id.button_gesture)
        voiceCommandButton = findViewById(R.id.button_voice)

        gestureValuesText = findViewById(R.id.gesture_text)

        originalColor = ContextCompat.getColor(this, R.color.original_color)
        highlightColor = ContextCompat.getColor(this, R.color.highlight_color)

        voiceCommandLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
                handleVoiceCommand(spokenText)
            }
        }

        forwardCommand.setOnClickListener {
            sendCommandToRobot(BleCommands.WALK_FORWARD)
            saveLog("Comanda trimisa: Inainte")
        }

        backwardCommand.setOnClickListener {
            sendCommandToRobot(BleCommands.BACKWARD)
            saveLog("Comanda trimisa: Inapoi")
        }

        restCommand.setOnClickListener {
            sendCommandToRobot(BleCommands.REST)
            saveLog("Comanda trimisa: Odihna")
        }

        leftCommand.setOnClickListener {
            sendCommandToRobot(BleCommands.WALK_LEFT)
            saveLog("Comanda trimisa: Stanga")
        }

        rightCommand.setOnClickListener {
            sendCommandToRobot(BleCommands.WALK_RIGHT)
            saveLog("Comanda trimisa: Dreapta")
        }

        logsButton.setOnClickListener {
            val intent = Intent(this@BotControlsActivity, LogActivity::class.java)
            startActivity(intent)
        }

        allCommandButton.setOnClickListener {
            val intent = Intent(this@BotControlsActivity, AllCommandsActivity::class.java)
            startActivity(intent)
        }

        gestureButton.setOnClickListener {
            if (!isGestureControl) {
                initGestureControl()
                gestureValuesText.visibility = View.VISIBLE
                gestureButton.setText("Opreste Controlul prin Gesturi")
            } else {
                stopGestureControl()
                gestureValuesText.visibility = View.INVISIBLE
            }
            isGestureControl = !isGestureControl
        }

        voiceCommandButton.setOnClickListener {
            startVoiceRecognition()
        }
    }

    private fun initGestureControl() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Toast.makeText(this, "Senzorul de accelerometru nu este disponibil pe acest dispozitiv", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopGestureControl() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Spune comanda pentru robot")

        try {
            voiceCommandLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            // Afișează un mesaj că dispozitivul nu suportă recunoașterea vocală
            Toast.makeText(this, "Dispozitivul tău nu suportă recunoașterea vocală", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleVoiceCommand(command: String) {
        voiceCommandMap.commandMap.entries.find { entry ->
            command.contains(entry.key, ignoreCase = true)
        }?.let { entry ->
            sendCommandToRobot(entry.value)
            saveLog("Comanda trimisă: ${entry.key.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }}")
        } ?: Toast.makeText(this, "Comandă necunoscută", Toast.LENGTH_SHORT).show()
    }

    private fun sendCommandToRobot(command: BleCommands) {
        bluetoothConnectionManager.writeCommand(SERVICIU_TX, CARACTERISTICA_TX, this, command)
    }


    private fun saveLog(command: String) {
        val dbHelper = LogDatabaseHelper(this)
        try {
            dbHelper.addLog(command)
        } catch (e: Exception) {
            Log.e("LogDatabaseHelper", "Failed to save log: $command", e)
        } finally {
            dbHelper.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorEventListener)
    }
}
