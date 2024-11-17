package com.example.starteraplication.data.repository

import android.content.ContentValues
import android.content.Context
import com.example.starteraplication.data.database.UserContract
import com.example.starteraplication.data.database.UserDbHelper
import com.example.starteraplication.model.User

class UserRepository(context: Context) {

    private val dbHelper = UserDbHelper(context)

    fun insertUser(user: User) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(UserContract.UserEntry.COLUMN_NAME, user.name)
            put(UserContract.UserEntry.COLUMN_AGE, user.age)
            put(UserContract.UserEntry.COLUMN_EMAIL, user.email)
        }
    }
}