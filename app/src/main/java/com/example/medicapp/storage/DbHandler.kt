package com.example.medicapp.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.medicapp.models.CreateUserModelInApi

class DbHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "DataBase.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "mydataBase"

        private const val ID = "id"
        private const val PASSWORD = "password"

        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val MIDDLE_NAME = "middleName"
        private const val BRITH = "brith"
        private const val GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PASSWORD + "TEXT)")
        db.execSQL(query)
    }

    fun setUserDate(user: CreateUserModelInApi) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(FIRST_NAME, user.firstname)
        values.put(LAST_NAME, user.lastname)
        values.put(MIDDLE_NAME, user.middlename)
        values.put(BRITH, user.bith)
        values.put(GENDER, user.pol)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getUserDate(): CreateUserModelInApi? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        var userModel: CreateUserModelInApi? = null

        if(cursor.moveToFirst()) {
            do {
                userModel = CreateUserModelInApi(
                    id = 1,
                    firstname = cursor.getString(0),
                    lastname = cursor.getString(1),
                    middlename = cursor.getString(2),
                    bith = cursor.getString(3),
                    pol = cursor.getString(4),
                    image = ""
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return userModel
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