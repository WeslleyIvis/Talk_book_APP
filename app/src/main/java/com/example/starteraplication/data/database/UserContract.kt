package com.example.starteraplication.data.database

import android.provider.BaseColumns

object  UserContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_AGE = "age"
        const val COLUMN_PHOTO_URL = "image_url"
        const val COLUMN_PASSWORD = "password"
    }
}