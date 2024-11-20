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

        fun handlerContext() {
            val intent = Intent(this, MainStartApp::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.button_login).setOnClickListener{
            val email = findViewById<TextView>(R.id.login_input_email).text.toString()
            val password = findViewById<TextView>(R.id.login_input_password).text.toString()
            val dbHelper = UserDbHelper(this)

            lifecycleScope.launch {
                try {
                    val isValid = withContext(Dispatchers.IO) {
                        dbHelper.validateUser(email, password)
                    }

                    if (isValid) {
                        handlerContext()
                    } else {
                        Toast.makeText(this@Login, "Login ou senha incorretos", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@Login, "Erro ao validar usuário", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}