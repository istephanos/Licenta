package com.example.petoibittlecontrol.mainController.logs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petoibittlecontrol.R

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        title = "Vizualizare comenzi trimise la robot"
    }
}