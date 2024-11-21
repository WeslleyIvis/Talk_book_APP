package com.example.starteraplication.data.database

import android.provider.BaseColumns

object BookContract {
    object BookEntry : BaseColumns {
        const val TABLE_NAME = "books"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR_ID = "author_id"
        const val COLUMN_GENRE = "genre"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_AUDIO_URL = "audio_url"
        const val COLUMN_PDF_URL = "pdf_url"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_RELEASE_DATE = "release_date"
        const val COLUMN_RATING = "rating"
    }
}