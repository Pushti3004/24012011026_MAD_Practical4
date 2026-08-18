package com.example.a24012011026_mad_practical4

import android.app.TimePickerDialog
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

class MainActivity : AppCompatActivity() {

    lateinit var textAlarm: TextView
    lateinit var cardSetAlarm: MaterialCardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textAlarm = findViewById<TextView>(R.id.Time1)
        cardSetAlarm = findViewById(R.id.card1)
        cardSetAlarm.visibility = View.GONE
        findViewById<MaterialButton>(R.id.CreateAlarm).setOnClickListener {

        }
        findViewById<MaterialButton>(R.id.CancelAlarm).setOnClickListener {

        }
    }
    private fun showTimeDialog(){
        val cldr: Calendar = Calendar.getInstance()
        val h:Int = cldr.get(Calendar.HOUR_OF_DAY)
        val n:Int = cldr.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
            this,{tp,sHour,sMinute->sendDialogDataToActivity(sHour,sMinute)},
            h,n,false
        )
        picker.show()
    }

    private fun sendDialogDataToActivity(hour: Int,minute: Int){
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year,month,day,hour,minute,0)
        textAlarm.text = SimpleDateFormat("hh mm ss a").format(alarmCalendar.time)
        Toast.makeText(
            this,
            "Time: hours:${hour}, minutes:${minute}" +
                    "mills:${alarmCalendar.timeInMillis}",
            Toast.LENGTH_SHORT
        ).show()
    }
}