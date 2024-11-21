package com.example.starteraplication.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class Login : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_window)

        findViewById<TextView>(R.id.btn_text_create_account).setOnClickListener {
            val intent = Intent(this, LoginCreateAccount::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.btn_text_create_account2).setOnClickListener {
            val intent = Intent(this, passwordRecovery::class.java)
            startActivity(intent)
        }
    }
}