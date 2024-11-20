package com.example.starteraplication.data.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.starteraplication.model.User

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "user_database.db"
        const val DATABASE_VERSION = 1
    }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
                        "${UserContract.UserEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${UserContract.UserEntry.COLUMN_NAME} TEXT," +
                        "${UserContract.UserEntry.COLUMN_AGE} INTEGER," +
                        "${UserContract.UserEntry.COLUMN_EMAIL} TEXT," +
                        "${UserContract.UserEntry.COLUMN_PASSWORD} TEXT)"
            )

            db.execSQL(
                "CREATE TABLE ${AuthorContract.AuthorEntry.TABLE_NAME} (" +
                        "${AuthorContract.AuthorEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${AuthorContract.AuthorEntry.COLUMN_NAME} TEXT," +
                        "${AuthorContract.AuthorEntry.COLUMN_BIO} TEXT," +
                        "${AuthorContract.AuthorEntry.COLUMN_AGE} INTEGER," +
                        "${AuthorContract.AuthorEntry.COLUMN_COUNTRY} TEXT," +
                        "${AuthorContract.AuthorEntry.COLUMN_PHOTO_URL} TEXT," +
                        "${AuthorContract.AuthorEntry.COLUMN_BIRTH_DATE} INTEGER)"
            )

            db.execSQL(
                "CREATE TABLE ${BookContract.BookEntry.TABLE_NAME} (" +
                        "${BookContract.BookEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${BookContract.BookEntry.COLUMN_TITLE} TEXT," +
                        "${BookContract.BookEntry.COLUMN_GENRE} TEXT," +
                        "${BookContract.BookEntry.COLUMN_DESCRIPTION} TEXT," +
                        "${BookContract.BookEntry.COLUMN_IMAGE_URL} TEXT," +
                        "${BookContract.BookEntry.COLUMN_AUDIO_URL} TEXT," +
                        "${BookContract.BookEntry.COLUMN_PDF_URL} TEXT," +
                        "${BookContract.BookEntry.COLUMN_DURATION} INTEGER," +
                        "${BookContract.BookEntry.COLUMN_RELEASE_DATE} TEXT," +
                        "${BookContract.BookEntry.COLUMN_RATING} REAL," +
                        "${BookContract.BookEntry.COLUMN_AUTHOR_ID} INTEGER," +
                        "FOREIGN KEY(${BookContract.BookEntry.COLUMN_AUTHOR_ID}) REFERENCES ${AuthorContract.AuthorEntry.TABLE_NAME}(${AuthorContract.AuthorEntry.COLUMN_ID}))"
                )
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

            val newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values)
            db.close()
            return newRowId
        }

        fun readAllUsers(): String {
            val users = mutableListOf<User>()
            val db = readableDatabase
            val cursor: Cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                null, null, null, null, null, null
            )
            with(cursor) {
                val id = getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME))
                val age = getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_AGE)).toString()
                val email = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL)).toInt()
                val password = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD))

                users.add(User(id, name, age, email, password))
            }
            cursor.close()
            return  users.joinToString("\n") { "${it.id} - ${it.name} - ${it.email} - ${it.age} - ${it.password}" }
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

        // Valida se o usuario existe para fazer login
        fun validateUser(email: String, password: String): Boolean {
            val db = readableDatabase
            val selection = "${UserContract.UserEntry.COLUMN_EMAIL} = ? AND ${UserContract.UserEntry.COLUMN_PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            val cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null, null
            )

            val isValid = cursor.moveToFirst()
            cursor.close()
            db.close()
            return isValid
        }

        fun printAllUsersToLog() {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM ${UserContract.UserEntry.TABLE_NAME}", null)

            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_ID))
                    val name = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME))
                    val email = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL))
                    val age= getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_AGE))
                    val password = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD))

                    Log.d("UserData", "ID: $id, Name: $name, Email: $email, Age: $age, Password: $password")
                }
            }

            cursor.close()
            db.close()
        }

    }

//?
