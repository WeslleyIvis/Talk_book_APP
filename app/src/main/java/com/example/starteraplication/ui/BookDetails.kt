package com.example.starteraplication.ui

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class BookDetails : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_details)

        val textnavigate = findViewById<TextView>(R.id.author_name_button)
        textnavigate.setOnClickListener {
            val intent = Intent(this, AuthorScreen::class.java)
            startActivity(intent)
        }

        val buttonNavigate = findViewById<Button>(R.id.button_ouvir)
        buttonNavigate.setOnClickListener {
            val intent = Intent(this, BookDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}