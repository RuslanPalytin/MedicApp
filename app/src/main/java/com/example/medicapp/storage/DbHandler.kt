package com.example.medicapp.storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "DataBase.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "mydataBase"

        private const val ID = "id"
        private const val PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PASSWORD + "TEXT)")
        db.execSQL(query)
    }

    fun addPassword(password: String) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(PASSWORD, password)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}