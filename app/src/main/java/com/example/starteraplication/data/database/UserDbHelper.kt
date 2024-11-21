package com.example.starteraplication.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


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
                        "${UserContract.UserEntry.COLUMN_PHOTO_URL} TEXT," +
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
            db.execSQL("DROP TABLE IF EXISTS ${AuthorContract.AuthorEntry.TABLE_NAME}")
            db.execSQL("DROP TABLE IF EXISTS ${BookContract.BookEntry.TABLE_NAME}")
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
    }

//?
