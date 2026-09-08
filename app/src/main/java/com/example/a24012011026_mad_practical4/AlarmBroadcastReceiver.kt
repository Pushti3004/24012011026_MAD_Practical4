package com.example.a24012011026_mad_practical4

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val SERVICE_KEY = "Service1"
        const val START_VAL = "start"
        const val STOP_VAL = "stop"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show()

        val action = intent.getStringExtra(SERVICE_KEY)

        val serviceIntent = Intent(context, AlarmService::class.java)

        if (action == START_VAL) {
            context.startService(serviceIntent)
        } else if (action == STOP_VAL) {
            context.stopService(serviceIntent)
        }
    }
}