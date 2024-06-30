package com.example.petoibittlecontrol.mainController.logs

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.util.LogDatabaseHelper
import androidx.appcompat.app.AlertDialog

class LogActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var logAdapter: LogAdapter
    private lateinit var logDatabaseHelper: LogDatabaseHelper
    private lateinit var buttonResetLogs: Button

    private fun resetLogs() {
        logDatabaseHelper.deleteAllLogs()
        Log.d("LogActivity", "Toate log-urile au fost resetate")
        loadLogs()  // Reîncarcă log-urile pentru a actualiza UI-ul
    }

    private fun loadLogs() {
        val cursor = logDatabaseHelper.allLogs
        logAdapter.swapCursor(cursor)  // Actualizează adapter-ul cu noile date
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmare Resetare")
        builder.setMessage("Ești sigur că vrei să resetezi toate log-urile? Această acțiune nu poate fi anulată.")

        builder.setPositiveButton("Da") { dialog, _ ->
            resetLogs()
            dialog.dismiss()
        }

        builder.setNegativeButton("Nu") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        title = "Vizualizare comenzi trimise către robot"
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        logDatabaseHelper = LogDatabaseHelper(this)
        val cursor: Cursor = logDatabaseHelper.allLogs

        logAdapter = LogAdapter(cursor)
        recyclerView.adapter = logAdapter

        buttonResetLogs = findViewById(R.id.button_reset_logs)
        buttonResetLogs.setOnClickListener {
            showResetConfirmationDialog()
        }
    }
}
