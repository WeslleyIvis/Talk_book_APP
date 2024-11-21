package com.example.starteraplication.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.starteraplication.data.database.UserContract
import com.example.starteraplication.data.database.UserDbHelper
import com.example.starteraplication.model.User

@Suppress("NAME_SHADOWING")
class UserRepository(context: Context) {

    private val dbHelper = UserDbHelper(context)

    fun createUser(user: User): Long {
        val db = dbHelper.writableDatabase

        // Verifica se já existe um usuário com o email cadastrado
        val cursor = db.query(
            UserContract.UserEntry.TABLE_NAME,
            arrayOf(UserContract.UserEntry.COLUMN_ID),
            "${UserContract.UserEntry.COLUMN_EMAIL} = ?",
            arrayOf(user.email),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            cursor.close()
            db.close()
            return -1
        } else {
            cursor.close()
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
    }

    fun readAllUsers(): String {
        val users = mutableListOf<User>()
        val db = dbHelper.writableDatabase
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
        val db = dbHelper.writableDatabase
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
        val db = dbHelper.writableDatabase
        val selection = "${UserContract.UserEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(user.id.toString())

        return db.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs)
    }

    // Valida se o usuario existe para fazer login
    fun validateUser(email: String, password: String): Boolean {
        val db = dbHelper.writableDatabase
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

    // Valida se o email já é cadastrado
    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val selection = "${UserContract.UserEntry.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            UserContract.UserEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var user: User? = null

        try {
            if (cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL))
                val age = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_AGE)).toInt()
                val photoUrl = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PHOTO_URL))

                user = User(id = null, name, email, age, photoUrl)
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Erro ao obter usuário por email", e)
            return null
        } finally {
            cursor.close()
            db.close()
        }

        return user
    }

    // Printa os usuarios cadastrados, USADO somente para teste

    fun printAllUsersToLog() {
        val db = dbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${UserContract.UserEntry.TABLE_NAME}", null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME))
                val email = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_EMAIL))
                val age= getInt(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_AGE))
                val password = getString(getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD))

                android.util.Log.d("UserData", "ID: $id, Name: $name, Email: $email, Age: $age, Password: $password")
            }
        }

        cursor.close()
        db.close()
    }

}