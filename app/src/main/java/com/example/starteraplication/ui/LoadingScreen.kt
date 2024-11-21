package com.example.starteraplication.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class LoadingScreen : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        supportActionBar?.hide()

        // Obtém a view raiz
        val rootView = findViewById<View>(android.R.id.content)

        // Define o listener
        rootView.setOnApplyWindowInsetsListener { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
            view.setPadding(
                statusBarInsets.left,
                statusBarInsets.top,
                statusBarInsets.right,
                0 // Ignorar insets da navigation bar
            )
            insets // Retornar insets para propagação
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}