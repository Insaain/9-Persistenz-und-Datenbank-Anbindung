package de.hhn.a9persistenzunddatenbank_anbindung.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createToDoTable = """
            CREATE TABLE todos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                priority INTEGER NOT NULL,
                deadline TEXT NOT NULL,
                status INTEGER NOT NULL CHECK (status IN (0, 1))
            )
        """
        db?.execSQL(createToDoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS todos")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "todo.db"
        const val DATABASE_VERSION = 1
    }
}