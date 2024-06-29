package com.example.petoibittlecontrol.commands

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.petoibittlecontrol.BleConstants
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.connection.BluetoothConnectionManager
import com.example.petoibittlecontrol.databinding.ActivityAllCommandsBinding

class AllCommandsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllCommandsBinding
    private var bluetoothConnectionManager: BluetoothConnectionManager = BluetoothConnectionManager.getInstance(this)


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCommandsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayout = binding.linearLayoutCommands

        // Function to create buttons
        fun createCommandButton(command: BleCommands, buttonText: String) {
            val button = Button(this).apply {
                text = buttonText
                setOnClickListener {
                    sendCommand(command)
                }
            }
            linearLayout.addView(button)
        }

        val blackColor = ContextCompat.getColor(this, R.color.black)
        val postureTextView = TextView(this).apply {
            text = "Postură"
            textColors
            textSize = 18f
            setPadding(0, 10, 0, 8)
            setTextColor(blackColor)
        }
        val movementTextView = TextView(this).apply {
            text = "Mers"
            textSize = 18f
            setPadding(0, 30, 0, 15)
            setTextColor(blackColor)
        }
        val behaviorTextView = TextView(this).apply {
            text = "Comportament"
            textSize = 18f
            setPadding(0, 30, 0, 15)
            setTextColor(blackColor)
        }

        linearLayout.addView(postureTextView)
        createCommandButton(BleCommands.KBALANCE, "Echilibru")
        createCommandButton(BleCommands.BUTTUP, "Fundul Sus")
        createCommandButton(BleCommands.CALIBRATION, "Calibrare")
        createCommandButton(BleCommands.DROPPED, "Căzut")
        createCommandButton(BleCommands.LIFTED, "Ridicat")
        createCommandButton(BleCommands.REST, "Odihnă")
        createCommandButton(BleCommands.SIT, "Așezare")
        createCommandButton(BleCommands.STRETCH, "Întindere")
        createCommandButton(BleCommands.ZERO, "Zero")

        linearLayout.addView(movementTextView)
        createCommandButton(BleCommands.BOUND_FORWARD, "Înaintare rapidă")
        createCommandButton(BleCommands.BACKWARD, "Înapoi")
        createCommandButton(BleCommands.BACKWARD_LEFT, "Înapoi stânga")
        createCommandButton(BleCommands.CRAWL_FORWARD, "Târâre înainte")
        createCommandButton(BleCommands.CRAWL_LEFT, "Târâre stânga")
        createCommandButton(BleCommands.GAP_FORWARD, "Salt înainte")
        createCommandButton(BleCommands.GAP_LEFT, "Salt stânga")
        createCommandButton(BleCommands.HALLOWEEN, "Halloween")
        createCommandButton(BleCommands.JUMP_FORWARD, "Sărire înainte")
        createCommandButton(BleCommands.PUSH_FORWARD, "Împinge înainte")
        createCommandButton(BleCommands.PUSH_LEFT, "Împinge stânga")
        createCommandButton(BleCommands.TROT_FORWARD, "Tropăit înainte")
        createCommandButton(BleCommands.TROT_LEFT, "Tropăit stânga")
        createCommandButton(BleCommands.STEP_ORIGIN, "Pas la origine")
        createCommandButton(BleCommands.SPING_LEFT, "Rotire la  stânga")
        createCommandButton(BleCommands.WALK_FORWARD, "Mers înainte")
        createCommandButton(BleCommands.WALK_LEFT, "Mers stânga")
        createCommandButton(BleCommands.WALK_RIGHT, "Mers dreapta")

        linearLayout.addView(behaviorTextView)
        createCommandButton(BleCommands.ANGRY, "Furie")
        createCommandButton(BleCommands.BACKFLIP, "Salt înapoi")
        createCommandButton(BleCommands.CHECK, "Verificare")
        createCommandButton(BleCommands.FRONT_FLIP, "Salt în față")
        createCommandButton(BleCommands.HI, "Salut")
        createCommandButton(BleCommands.PLAY_DEAD, "Prefă-te mort")
        createCommandButton(BleCommands.PEE, "Pipi")
        createCommandButton(BleCommands.PUSH_UPS, "Flotări")
        createCommandButton(BleCommands.PUSH_UPS_ONE_HAND, "Flotăre cu o mână")
        createCommandButton(BleCommands.RECOVER, "Recuperare")
        createCommandButton(BleCommands.ROLL, "Rostogolește-te")
        createCommandButton(BleCommands.TEST, "Test")

        //createCommandButton(BleCommands.GOOD_BOY, "Băiat bun")
        //createCommandButton(BleCommands.HANDSTAND, "Stând în mâini")
        //createCommandButton(BleCommands.HUG, "Îmbrățișare")
        //createCommandButton(BleCommands.COME_HERE, "Vino Aici")
        //createCommandButton(BleCommands.DIG, "Sapă")
        //createCommandButton(BleCommands.WAVE_HEAD, "Flutură capul")
        //createCommandButton(BleCommands.ALL_JOINT_AT_0, "Toate articulațiile La 0")
        //createCommandButton(BleCommands.BOXING, "Box")
        //createCommandButton(BleCommands.CHEERS, "Ura")
        //createCommandButton(BleCommands.SCRATCH, "Scărpină")
        //createCommandButton(BleCommands.SNIFF, "Miros")
        //createCommandButton(BleCommands.BE_TABLE, "Fii masă")
        //createCommandButton(BleCommands.HAND_SHAKE, "Strângere de mână")
        //createCommandButton(BleCommands.HANDS_UP, "Mâinile sus")
        //createCommandButton(BleCommands.JUMP, "Sărit")
        //createCommandButton(BleCommands.KICK, "Lovitură")
        //createCommandButton(BleCommands.LEAP_OVER, "Sari peste")
        //createCommandButton(BleCommands.MOON_WALK, "Moonwalk")
        //createCommandButton(BleCommands.NOD, "Dă din cap")
    }

    private fun sendCommand(command: BleCommands) {
        bluetoothConnectionManager.writeCommand(
            BleConstants.SERVICIU_TX, BleConstants.CARACTERISTICA_TX, this, command)
    }
}
