package com.example.starteraplication.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class passwordRecovery : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_recovery)

        val buttons = listOf(findViewById<Button>(R.id.create_acc_button_to_go_back), findViewById<TextView>(R.id.create_acc_text_login))

        fun handlerWindow() {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        buttons.forEach { button ->
            button.setOnClickListener {
                handlerWindow()
            }
        }
    }
}