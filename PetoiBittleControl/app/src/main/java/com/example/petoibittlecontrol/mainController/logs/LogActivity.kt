package com.example.petoibittlecontrol.mainController.logs

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.util.LogDatabaseHelper

class LogActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var logAdapter: LogAdapter
    private lateinit var logDatabaseHelper: LogDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        title = "Vizualizare comenzi trimise catre robot"
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        logDatabaseHelper = LogDatabaseHelper(this)
        val cursor: Cursor = logDatabaseHelper.allLogs

        logAdapter = LogAdapter(cursor)
        recyclerView.adapter = logAdapter
    }
}