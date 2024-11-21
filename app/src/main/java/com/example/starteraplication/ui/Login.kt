package com.example.starteraplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class Login : AppCompatActivity() {
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