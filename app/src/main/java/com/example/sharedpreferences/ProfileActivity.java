package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    TextView textViewName;
    TextView textViewAddress;
    TextView textViewAge;
    TextView textViewCurrentTime;
    TextView textViewDiffInTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Helper.flag == 1) {
            setContentView(R.layout.activity_profile);
            Helper.currDate = new Date();
            setDateDiff(Helper.date, Helper.currDate);
            setData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Helper.flag = 2;
        this.finish();
    }

    private String setDateDiff(Date date, Date currDate) {
        String result = "";
        Calendar regDate = Calendar.getInstance();
        Calendar profDate = Calendar.getInstance();
        regDate.setTime(date);
        profDate.setTime(currDate);
        Helper.years = profDate.get(Calendar.YEAR) - regDate.get(Calendar.YEAR);
        Helper.months = profDate.get(Calendar.MONTH) - regDate.get(Calendar.MONTH);
        Helper.days = profDate.get(Calendar.DAY_OF_MONTH) - regDate.get(Calendar.DAY_OF_MONTH);
        Helper.hours = profDate.get(Calendar.HOUR_OF_DAY) - regDate.get(Calendar.HOUR_OF_DAY);
        Helper.mins = profDate.get(Calendar.MINUTE) - regDate.get(Calendar.MINUTE);
        Helper.secs = profDate.get(Calendar.SECOND) - regDate.get(Calendar.SECOND);
        if (Helper.years > 0)
            result += Helper.years + " years ";
        if (Helper.months > 0)
            result += Helper.months + " months ";
        if (Helper.days > 0)
            result += Helper.days + " days ";
        if (Helper.hours > 0)
            result += Helper.hours + " hours ";
        if (Helper.mins > 0)
            result += Helper.mins + " mins ";
        if (Helper.secs < 0)
            Helper.secs += 60;
        result += Helper.secs + " secs ";
        return result;
    }

    private void setData() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewCurrentTime = (TextView) findViewById(R.id.textViewCurrentTime);
        textViewDiffInTime = (TextView) findViewById(R.id.textViewDiffInTime);
        sharedPref = getSharedPreferences("SharedPref1", MODE_PRIVATE);
        Helper.name = sharedPref.getString("name", null);
        Helper.address = sharedPref.getString("address", null);
        Helper.age = sharedPref.getInt("age", 0);
        Helper.date = new Date(sharedPref.getLong("date", 0));
        Helper.currDate = new Date();
        Helper.diffInTime = setDateDiff(Helper.date, Helper.currDate);
        Helper.flag = 1;
        textViewName.setText(String.format("Name : %s", Helper.name));
        textViewAddress.setText(String.format("Address : %s", Helper.address));
        textViewAge.setText("Age : " + Helper.age);
        textViewCurrentTime.setText(String.format("Current Date : %s", Helper.currDate.toString()));
        textViewDiffInTime.setText(String.format("Difference in Time : %s", Helper.diffInTime));
    }
}
