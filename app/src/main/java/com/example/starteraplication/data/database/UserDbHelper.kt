package com.example.starteraplication.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.starteraplication.model.User

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
    { companion object {
        const val DATABASE_NAME = "user_database.db"
        const val DATABASE_VERSION = 1
    }

        override fun onCreate(db: SQLiteDatabase) {
            val SQL_CREATE_ENTRIES =
                "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
                        "${UserContract.UserEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${UserContract.UserEntry.COLUMN_NAME} TEXT," +
                        "${UserContract.UserEntry.COLUMN_AGE} INTEGER," +
                        "${UserContract.UserEntry.COLUMN_EMAIL} TEXT," +
                        "${UserContract.UserEntry.COLUMN_PASSWORD} TEXT)"

            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, ondVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${UserContract.UserEntry.TABLE_NAME}")
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        // Crud

        fun createUser(user: User): Long {
            val db = writableDatabase
            val values = contentValuesOf().apply {
                put(UserContract.UserEntry.COLUMN_NAME, user.name)
                put(UserContract.UserEntry.COLUMN_AGE, user.age)
                put(UserContract.UserEntry.COLUMN_EMAIL, user.email)
                put(UserContract.UserEntry.COLUMN_PASSWORD, user.password)
            }

            return db.insert(UserContract.UserEntry.TABLE_NAME, null, values)
        }

        fun readAllUsers(): List<User> {
            val users = mutableListOf<User>()
            val db = readableDatabase
            val cursor: Cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                null, null, null, null, null, null
            )
            with(cursor) {
                val name = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME))
                val age = getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_AGE))
                val email = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL))
                val password = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD))
            }
            cursor.close()
            return  users
        }

        fun updateUser(user: User): Int {
            val db = writableDatabase
            val values = contentValuesOf().apply {
                put(UserContract.UserEntry.COLUMN_NAME, user.name)
                put(UserContract.UserEntry.COLUMN_AGE, user.age)
                put(UserContract.UserEntry.COLUMN_EMAIL, user.email)
                put(UserContract.UserEntry.COLUMN_PASSWORD, user.password)
            }
            val selection = "${UserContract.UserEntry.COLUMN_ID} = ?"
            val selectionArgs = arrayOf(user.id.toString())

            return db.update(UserContract.UserEntry.TABLE_NAME, values, selection, selectionArgs)
        }

        fun deleteUser(user: User): Int {
            val db = writableDatabase
            val selection = "${UserContract.UserEntry.COLUMN_ID} = ?"
            val selectionArgs = arrayOf(user.id.toString())

            return db.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        }
    }

//?