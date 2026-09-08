package com.example.a24012011026_mad_practical4

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlarmService : Service() {

    lateinit var mediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }

        return START_STICKY
    }

    override fun onDestroy() {

        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        super.onDestroy()
    }
}