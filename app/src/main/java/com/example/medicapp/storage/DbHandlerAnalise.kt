package com.example.medicapp.storage

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.medicapp.models.CatalogModel

class DbHandlerAnalise(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "databaseAnalise.db"
        private const val DB_VERSION = 4
        private const val TABLE_NAME = "analise_table"

        private const val ID = "id"
        private const val NAME = "name"
        private const val PRICE = "price"
        private const val PEOPLE_NUMBER = "people_number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + PRICE + " TEXT,"
                + PEOPLE_NUMBER + " TEXT)")
        db.execSQL(query)
    }

    fun addItem(name: String, price: String) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME, name)
        values.put(PRICE, price)
        values.put(PEOPLE_NUMBER, "1")

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getItems(): MutableList<CatalogModel> {
        val db = this.writableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val catalog: MutableList<CatalogModel> = mutableListOf()

        if(cursor.moveToFirst()) {
            do {
                catalog.add(
                    CatalogModel(
                        id = 1,
                        name = cursor.getString(1),
                        description = "",
                        price = cursor.getString(2),
                        category = "",
                        time_result = "",
                        preparation = cursor.getString(3),
                        bio = ""
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return catalog
    }

    fun updateItem(name: String, price: String, oldNumber: String, newNumber: String) {
        val db = this.readableDatabase
        val values = ContentValues()

        values.put(NAME, name)
        values.put(PRICE, price)
        values.put(PEOPLE_NUMBER, oldNumber)

        db.update(TABLE_NAME, values, "$PEOPLE_NUMBER=?", arrayOf(newNumber))
        db.close()
    }

    fun deleteItem(itemName: String) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$NAME=?", arrayOf(itemName))
        db.close()
    }

    fun deleteAllItems(items: MutableList<CatalogModel>) {
        val db = this.writableDatabase

        for(i in 0 until items.size) {
            db.delete(TABLE_NAME, "$NAME=?", arrayOf(items[i].name))
        }

        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}