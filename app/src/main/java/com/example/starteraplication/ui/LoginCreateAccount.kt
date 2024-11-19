package com.example.starteraplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.starteraplication.R
import com.example.starteraplication.data.database.UserDbHelper
import com.example.starteraplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginCreateAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account_window)

        val buttons = listOf(findViewById<Button>(R.id.create_acc_button_cancel), findViewById<TextView>(R.id.create_acc_text_login))

        fun handlerWindow() {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        buttons.forEach { button ->
            button.setOnClickListener {
                handlerWindow()
            }
        }

        findViewById<Button>(R.id.create_acc_button_confirm).setOnClickListener{
            val name = findViewById<TextView>(R.id.create_acc_input_name).text.toString()
            val email = findViewById<TextView>(R.id.create_acc_input_email).text.toString()
            val password = findViewById<TextView>(R.id.create_acc_input_password).text.toString()
            val confirmPassword = findViewById<TextView>(R.id.create_acc_input_confirm_password).text.toString()
            val passwordMath = password.contentEquals(confirmPassword)

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || password.isEmpty()) {
                println("preencha todos os campos")
            } else if (!passwordMath) {
                println("As senhas não são iguais")
            } else {
                val dbHelper = UserDbHelper(this)
                val newUser = User(id = null,name, email, age = 0, password)

                lifecycleScope.launch {
                    try {
                        val newRowId = withContext(Dispatchers.IO) {
                            dbHelper.createUser(newUser)
                        }

                        if(newRowId != -1L) {
                            Toast.makeText(this@LoginCreateAccount, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                            handlerWindow()
                        } else
                            Toast.makeText(this@LoginCreateAccount, "Erro ao criar usuário", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.e("LoginCreateAccount", "Erro ao criar usuário", e)
                    }
                }
            }
        }
    }
}