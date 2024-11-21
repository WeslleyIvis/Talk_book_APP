package com.example.starteraplication.data.database

import android.provider.BaseColumns

object AuthorContract {
    object AuthorEntry : BaseColumns {
        const val TABLE_NAME = "authors"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_BIO = "bio"
        const val COLUMN_AGE = "age"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_PHOTO_URL = "photo_url"
        const val COLUMN_BIRTH_DATE = "birth_date"
    }
}