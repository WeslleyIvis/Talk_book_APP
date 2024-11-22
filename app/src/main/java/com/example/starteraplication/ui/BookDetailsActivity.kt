package com.example.starteraplication.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.starteraplication.R


class BookDetailsActivity : AppCompatActivity() {

    private var isTextExpanded = true // Controla o estado do texto
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var currentPosition: Int = 0

    private lateinit var seekbar: SeekBar
    private lateinit var currentTimeText: TextView
    private lateinit var totalTime: TextView
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_sound)

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val toggleButton = findViewById<ImageView>(R.id.closeButton)

        val btnStarterSound = findViewById<ImageView>(R.id.playPauseButton)
        val musicPath = R.raw.harrypotter1

        seekbar = findViewById(R.id.seekBar)
        currentTimeText = findViewById(R.id.currentTime)
        totalTime = findViewById(R.id.totalTime)

        toggleButton.setOnClickListener {
            toggleScrollView(scrollView, toggleButton)
        }

        btnStarterSound.setOnClickListener {
            if (isPlaying) {
                btnStarterSound.setImageResource(R.drawable.start_song)
                stopAudio()
            } else {
                playMusic(musicPath)
                btnStarterSound.setImageResource(R.drawable.pause_song)
            }
            isPlaying = !isPlaying
        }
    }

    private fun playMusic(musicResId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, musicResId)
            mediaPlayer?.setOnCompletionListener {
                isPlaying = false
                handler.removeCallbacks(updateSeekBarTask)
            }
        }

        mediaPlayer?.apply {
            if (!isPlaying) {
                seekTo(currentPosition)
                start()

                // Configurar duração total
                val duration = duration
                seekbar.max = duration
                currentTimeText.text = formatTime(currentPosition)
                totalTime.text = formatTime(duration)

                // Iniciar atualização do SeekBar
                handler.post(updateSeekBarTask)
            }
        }
    }

    private fun stopAudio() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
                this@BookDetailsActivity.currentPosition = currentPosition
                this@BookDetailsActivity.isPlaying = false
                this@BookDetailsActivity.isPlaying = false
                handler.removeCallbacks(updateSeekBarTask)
            }
        }
    }

    private val updateSeekBarTask = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                val currentTime = it.currentPosition
                seekbar.progress = currentTime
                currentTimeText.text = formatTime(currentTime)
                handler.postDelayed(this, 1000) // Atualiza a cada segundo
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(milliseconds: Int): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = (milliseconds / 1000) % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(updateSeekBarTask)
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
