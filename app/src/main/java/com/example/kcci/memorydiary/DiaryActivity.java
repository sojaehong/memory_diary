package com.example.kcci.memorydiary;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.kcci.memorydiary.adapter.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Integer> spinnerList;
    private int mWakeupHour, mWakeupMinute, mSleepHour, mSleepMinute;
    private int timeType;
    private Calendar calendar;

    private Spinner spinnerWeather;
    private TextView txtWakeupTime, txtSleepTime, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        spinnerWeather = findViewById(R.id.spinner_weather);
        txtWakeupTime = findViewById(R.id.txt_wakeup_time);
        txtSleepTime = findViewById(R.id.txt_sleep_time);
        txtDate = findViewById(R.id.txt_date);

        calender();

        setTxtDate();

        spinnerList = new ArrayList<>();
        spinnerList.add(R.drawable.if_cloudy);
        spinnerList.add(R.drawable.if_cloud);
        spinnerList.add(R.drawable.if_rain_cloud);
        spinnerList.add(R.drawable.if_snow_cloud);
        spinnerList.add(R.drawable.if_flash_cloud);

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(spinnerList, getApplicationContext());

        spinnerWeather.setAdapter(spinnerAdapter);

        txtWakeupTime.setOnClickListener( this );
        txtSleepTime.setOnClickListener( this );
    }

    private void calender(){
        calendar = new GregorianCalendar();

        mWakeupHour = calendar.get(Calendar.HOUR_OF_DAY);
        mWakeupMinute = calendar.get(Calendar.MINUTE);

        mSleepHour = calendar.get(Calendar.HOUR_OF_DAY);
        mSleepMinute = calendar.get(Calendar.MINUTE);
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(timeType == 1) {
                mWakeupHour = hourOfDay;
                mWakeupMinute = minute;
                setTxtWakeupTime();
            }

            if(timeType == 2){
                mSleepHour = hourOfDay;
                mSleepMinute = minute;
                setTxtSleepTime();
            }

        }
    };

    public void setTxtWakeupTime() {
        txtWakeupTime.setText(String.format("%d시 %d분", mWakeupHour, mWakeupMinute));
    }
    public void setTxtSleepTime() {
        txtSleepTime.setText(String.format("%d시 %d분", mSleepHour, mSleepMinute));
    }

    public void setTxtDate(){
        Intent intent =  getIntent();
        String date = intent.getStringExtra("date");
        txtDate.setText(date);
    }

    @Override
    public void onClick(View v) {
        if(v == txtWakeupTime) {
            timeType = 1;
            new TimePickerDialog(DiaryActivity.this, onTimeSetListener, mWakeupHour, mWakeupMinute, false).show();
        }

        if(v == txtSleepTime) {
            timeType = 2;
            new TimePickerDialog(DiaryActivity.this, onTimeSetListener, mSleepHour, mSleepMinute, false).show();
        }

    }
}
