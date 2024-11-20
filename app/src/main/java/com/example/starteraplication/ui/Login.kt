package com.example.starteraplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.starteraplication.R
import com.example.starteraplication.data.database.UserDbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_window)


        findViewById<TextView>(R.id.btn_text_create_account).setOnClickListener {
            val intent = Intent(this, LoginCreateAccount::class.java)
            startActivity(intent)
        }


    }
}