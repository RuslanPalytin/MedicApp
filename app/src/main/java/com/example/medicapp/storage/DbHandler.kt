package com.example.medicapp.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.medicapp.models.CreateUserModelInApi

class DbHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "DataBase2.db"
        private const val DB_VERSION = 5
        private const val TABLE_NAME = "myDataBase"

        private const val ID = "id"
        private const val PASSWORD = "password"

        private const val FIRST_NAME = "_firstName"
        private const val LAST_NAME = "_lastName"
        private const val MIDDLE_NAME = "_middleName"
        private const val BRITH = "_brith"
        private const val GENDER = "_gender"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + MIDDLE_NAME + " TEXT,"
                + BRITH + " TEXT,"
                + GENDER + " TEXT)")
        db.execSQL(query)
    }

    fun setUserDate(user: CreateUserModelInApi) {

        Log.d("MyLog", user.toString())

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(FIRST_NAME, user.firstname)
        values.put(LAST_NAME, user.lastname)
        values.put(MIDDLE_NAME, user.middlename)
        values.put(BRITH, user.bith)
        values.put(GENDER, user.pol)

        Log.d("MyLog", values.toString())

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getUserDate(): CreateUserModelInApi {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        var userModel = CreateUserModelInApi(1, "", "", "", "", "", "")

        if(cursor.moveToFirst()) {
            do {
                userModel = CreateUserModelInApi(
                    id = cursor.getInt(0),
                    firstname = cursor.getString(1),
                    lastname = cursor.getString(2),
                    middlename = cursor.getString(3),
                    bith = cursor.getString(4),
                    pol = cursor.getString(5),
                    image = ""
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return userModel
    }

    fun updateUserData(oldModel: CreateUserModelInApi, newModel: CreateUserModelInApi) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(FIRST_NAME, oldModel.firstname)
        values.put(LAST_NAME, oldModel.lastname)
        values.put(MIDDLE_NAME, oldModel.middlename)
        values.put(BRITH, oldModel.bith)
        values.put(GENDER, oldModel.pol)

        db.update(TABLE_NAME, values, "$FIRST_NAME=?", arrayOf(newModel.firstname))
        db.update(TABLE_NAME, values, "$LAST_NAME=?", arrayOf(newModel.lastname))
        db.update(TABLE_NAME, values, "$MIDDLE_NAME=?", arrayOf(newModel.middlename))
        db.update(TABLE_NAME, values, "$BRITH=?", arrayOf(newModel.bith))
        db.update(TABLE_NAME, values, "$GENDER=?", arrayOf(newModel.pol))
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