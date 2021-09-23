package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private String name;
    private String address;
    private int age;
    private Date date;
    private Date currDate;
    private int years;
    private int months;
    private int days;
    private int hours;
    private int mins;
    private int secs;
    private SharedPreferences sharedPref;
    TextView textViewName;
    TextView textViewAddress;
    TextView textViewAge;
    TextView textViewCurrentTime;
    TextView textViewYear;
    TextView textViewMonth;
    TextView textViewDay;
    TextView textViewMinute;
    TextView textViewSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewCurrentTime = (TextView) findViewById(R.id.textViewCurrentTime);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        textViewDay = (TextView) findViewById(R.id.textViewDay);
        textViewMinute = (TextView) findViewById(R.id.textViewMinute);
        textViewSecond = (TextView) findViewById(R.id.textViewSecond);
        sharedPref = getSharedPreferences("SharedPref1",MODE_PRIVATE);
        name = sharedPref.getString("name",null);
        address = sharedPref.getString("address",null);
        age = sharedPref.getInt("age",0);
        date = new Date(sharedPref.getLong("date",0));
        currDate = new Date();
        setDateDiff(date,currDate);
        MainActivity.flag = 1;
        setData();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(MainActivity.flag==1){
            currDate = new Date();
            setDateDiff(date,currDate);
            setData();
        }
    }

    private void setDateDiff(Date date, Date currDate){
        Calendar regDate = Calendar.getInstance();
        Calendar profDate = Calendar.getInstance();
        regDate.setTime(date);
        profDate.setTime(currDate);
        years = profDate.get(Calendar.YEAR)-regDate.get(Calendar.YEAR);
        months = profDate.get(Calendar.MONTH)-regDate.get(Calendar.MONTH);
        days = profDate.get(Calendar.DAY_OF_MONTH)-regDate.get(Calendar.DAY_OF_MONTH);
        hours = profDate.get(Calendar.HOUR_OF_DAY)-regDate.get(Calendar.HOUR_OF_DAY);
        mins = profDate.get(Calendar.MINUTE)-regDate.get(Calendar.MINUTE);
        secs = profDate.get(Calendar.SECOND)-regDate.get(Calendar.SECOND);
        if(secs<0)
            secs += 60;
    }

    private void setData(){
        textViewName.setText(String.format("Name : %s", name));
        textViewAddress.setText(String.format("Address : %s", address));
        textViewAge.setText(String.valueOf("Age : " + age));
        textViewCurrentTime.setText(String.format("Current Date : %s", currDate.toString()));
        textViewYear.setText(String.valueOf("Years : " + years));
        textViewMonth.setText(String.valueOf("Months : " + months));
        textViewDay.setText(String.valueOf("Days : " + days));
        textViewMinute.setText(String.valueOf("Minutes : " + mins));
        textViewSecond.setText(String.valueOf("Seconds : " + secs));
    }
}
