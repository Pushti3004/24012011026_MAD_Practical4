package com.example.a24012011026_mad_practical4

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateAlarm: MaterialButton
    private lateinit var btnCancelAlarm: MaterialButton
    private lateinit var txtCurrentTime: TextView
    private lateinit var txtAlarmTime: TextView

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        btnCreateAlarm = findViewById(R.id.CreateAlarm)
        btnCancelAlarm = findViewById(R.id.CancelAlarm)
        txtCurrentTime = findViewById(R.id.Time1)
        txtAlarmTime = findViewById(R.id.Time2)

        txtCurrentTime.text = SimpleDateFormat(
            "hh:mm:ss a MMM,dd yyyy",
            Locale.getDefault()
        ).format(calendar.time)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        btnCreateAlarm.setOnClickListener {

            TimePickerDialog(
                this,
                { _, hour, minute ->

                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)

                    txtAlarmTime.text = SimpleDateFormat(
                        "hh:mm:ss a",
                        Locale.getDefault()
                    ).format(calendar.time)

                    val intent = Intent(this, AlarmBroadcastReceiver::class.java)

                    intent.putExtra(
                        AlarmBroadcastReceiver.SERVICE_KEY,
                        AlarmBroadcastReceiver.START_VAL
                    )

                    val pendingIntent = PendingIntent.getBroadcast(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ).show()
        }

        btnCancelAlarm.setOnClickListener {

            val intent = Intent(this, AlarmBroadcastReceiver::class.java)

            intent.putExtra(
                AlarmBroadcastReceiver.SERVICE_KEY,
                AlarmBroadcastReceiver.STOP_VAL
            )

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)

            sendBroadcast(intent)

            txtAlarmTime.text = "00 : 00"
        }
    }
}