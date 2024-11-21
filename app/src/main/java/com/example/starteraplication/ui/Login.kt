package com.example.starteraplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.starteraplication.R
import com.example.starteraplication.data.database.UserDbHelper
import com.example.starteraplication.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_window)

        findViewById<TextView>(R.id.btn_text_create_account).setOnClickListener {
            val intent = Intent(this, LoginCreateAccount::class.java)
            startActivity(intent)
        }

        fun handlerContext() {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.login_input_email).setOnClickListener{
            //UserDbHelper(this).onUpgrade(UserDbHelper(this).writableDatabase, 1 ,1)
            UserRepository(this).printAllUsersToLog()
        }

        findViewById<Button>(R.id.button_login).setOnClickListener{
            val email = findViewById<TextView>(R.id.login_input_email).text.toString()
            val password = findViewById<TextView>(R.id.login_input_password).text.toString()
            val dbHelper = UserRepository(this)

            lifecycleScope.launch {
                try {
                    val isValid = withContext(Dispatchers.IO) {
                        dbHelper.validateUser(email, password)
                    }

                    android.util.Log.d("UserData", "isValid: $isValid")

                    if (isValid) {
                        handlerContext()

                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login, "Login ou senha incorretos", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@Login, "Erro ao validar usuário", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}