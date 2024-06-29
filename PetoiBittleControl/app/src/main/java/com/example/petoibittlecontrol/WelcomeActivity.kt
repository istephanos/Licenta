package com.example.petoibittlecontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.petoibittlecontrol.mainController.ScanControllerActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var btnStart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnStart = findViewById(R.id.btn_start)

        btnStart.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, ScanControllerActivity::class.java)
            startActivity(intent)
        }
    }
}