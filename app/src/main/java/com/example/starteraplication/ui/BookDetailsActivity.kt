package com.example.starteraplication.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R

class BookDetailsActivity : AppCompatActivity() {

    private var isTextExpanded = true // Controla o estado do texto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_sound)

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val toggleButton = findViewById<ImageView>(R.id.closeButton)

        toggleButton.setOnClickListener {
            toggleScrollView(scrollView, toggleButton)
        }
    }

    private fun toggleScrollView(scrollView: ScrollView, toggleButton: ImageView) {
        if (isTextExpanded) {
            // Animação para mover o conteúdo para baixo
            ObjectAnimator.ofFloat(scrollView, "translationY", 0f, scrollView.height.toFloat()).apply {
                duration = 300
                start()
            }
            scrollView.postDelayed({
                scrollView.visibility = View.GONE // Esconde o conteúdo após a animação
            }, 300)
        } else {
            // Torna visível antes de animar
            scrollView.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(scrollView, "translationY", scrollView.height.toFloat(), 0f).apply {
                duration = 300
                start()
            }
        }

        // Animação de rotação da seta
        ObjectAnimator.ofFloat(toggleButton, "rotation", if (isTextExpanded) 0f else 180f, if (isTextExpanded) 180f else 0f).apply {
            duration = 300
            start()
        }

        // Alterna o estado
        isTextExpanded = !isTextExpanded
    }
}
