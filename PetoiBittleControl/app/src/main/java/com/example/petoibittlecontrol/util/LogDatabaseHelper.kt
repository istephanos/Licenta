package com.example.petoibittlecontrol.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class LogDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_LOGS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COMMAND + " TEXT,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")")
        db.execSQL(CREATE_LOGS_TABLE)
        Log.d("DatabaseHelper", "Database created with table: $TABLE_NAME")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
        Log.d("DatabaseHelper", "Database upgraded to version: $newVersion")
    }

    fun addLog(command: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_COMMAND, command)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.d("DatabaseHelper", "Log added: $command, Result: $result")
    }

    val allLogs: Cursor
        get() {
            val db = this.readableDatabase
            val cursor = db.query(
                TABLE_NAME, arrayOf(COLUMN_ID, COLUMN_COMMAND, COLUMN_TIMESTAMP),
                null, null, null, null, COLUMN_TIMESTAMP + " DESC"
            )
            Log.d("DatabaseHelper", "Logs retrieved: ${cursor.count}")
            return cursor
        }

    companion object {
        private const val DATABASE_NAME = "log.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "logs"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMMAND = "command"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }
}
