package com.example.app_alarmclock;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import java.time.LocalTime;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    TextView txtTime;
    Button btnTime,btncancel;
    TimePicker timePicker;
    Calendar dateTime = Calendar.getInstance();
    Intent intent;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int seconds;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTime = findViewById(R.id.txtDateTime);
        btnTime = findViewById(R.id.btnTime);
        timePicker = findViewById(R.id.timePicker);
        btncancel= findViewById(R.id.btncancel);
        this.timePicker.setIs24HourView(true);
//Установка начальной даты и времени
        txtTime.setText("Будильник не установлен!");
        btnTime.setText("Устновить будильник");

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timePicker.getMinute()<10)
                txtTime.setText(timePicker.getHour()+ ":0"+timePicker.getMinute());
                else
                    txtTime.setText(timePicker.getHour()+ ":"+timePicker.getMinute());
        LocalTime txttimenow = LocalTime.now();
        LocalTime txttimealarm = LocalTime.parse(txtTime.getText().toString());
        int sec_now = txttimenow.getHour() * 60 * 60 + txttimenow.getMinute() * 60 + txttimenow.getSecond();
        int sec_alarm = txttimealarm.getHour() * 60 * 60 + txttimealarm.getMinute() * 60 + txttimealarm.getSecond();
        seconds = 0;
        if (sec_now>sec_alarm)
        {
            seconds = 86400-(sec_now-sec_alarm);
        }else
        {
            seconds = (sec_alarm-sec_now);
        }
        intent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000L,
                pendingIntent);
        btncancel.setVisibility(View.VISIBLE);
        btnTime.setText("Изменить время");
            }
        });

    }
    public void cancel_onClick(View view) {
            alarmManager.cancel(pendingIntent);
            btnTime.setText("Устновить будильник");
            txtTime.setText("Будильник не установлен!");
            btncancel.setVisibility(View.INVISIBLE);
    }

}
