package com.example.petoibittlecontrol.mainController.logs

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petoibittlecontrol.R

class LogAdapter(private var cursor: Cursor) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var commandTextView: TextView
        var timestampTextView: TextView

        init {
            commandTextView = itemView.findViewById<TextView>(R.id.command_text)
            timestampTextView = itemView.findViewById<TextView>(R.id.timestamp_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.log_item, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            val command = cursor.getString(cursor.getColumnIndexOrThrow("command"))
            val timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
            holder.commandTextView.text = command
            holder.timestampTextView.text = timestamp
        }
    }



    override fun getItemCount(): Int {
        return cursor.count
    }

    fun swapCursor(newCursor: Cursor?) {
        cursor?.close()
        if (newCursor != null) {
            cursor = newCursor
        }
        if (newCursor != null) {
            notifyDataSetChanged()
        }
    }
}
