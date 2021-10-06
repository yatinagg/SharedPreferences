package com.example.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

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
        setDataToFile();
    }

    private void setDataToFile(){
        File file = new File(ProfileActivity.this.getFilesDir(), "text");
        System.out.println(file);
        file.mkdir();
        File addFile = null;
        try {
            addFile = new File(file, "sample");
            FileWriter writer = new FileWriter(addFile);
            writer.append(SharedPrefHelper.getName()).append("\n");
            writer.append(SharedPrefHelper.getAddress()).append("\n");
            writer.append(String.valueOf(SharedPrefHelper.getAge())).append("\n");
            Date date = SharedPrefHelper.getDate();
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateformat));
            String dateFor = dateFormat.format(date);
            writer.append(dateFor + "\n");
            writer.append(String.valueOf(SharedPrefHelper.getFlag())).append("\n");
            writer.flush();
            writer.close();
        } catch (Exception e) { }
        SharedPrefHelper.setFlag(1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefHelper.getFlag() == 1) {
            setContentView(R.layout.activity_profile);
            SharedPrefHelper.setCurrDate(new Date());
            setDateDiff(SharedPrefHelper.getDate(), SharedPrefHelper.getCurrDate());
            setData();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefHelper.setFlag(2);
        this.finish();
    }

    private String setDateDiff(Date date, Date currDate) {
        String result = "";

        Calendar regDate = Calendar.getInstance();
        Calendar profDate = Calendar.getInstance();
        regDate.setTime(date);
        profDate.setTime(currDate);
        SharedPrefHelper.setYears(profDate.get(Calendar.YEAR) - regDate.get(Calendar.YEAR));
        SharedPrefHelper.setMonths(profDate.get(Calendar.MONTH) - regDate.get(Calendar.MONTH));
        SharedPrefHelper.setDays(profDate.get(Calendar.DAY_OF_MONTH) - regDate.get(Calendar.DAY_OF_MONTH));
        SharedPrefHelper.setHours(profDate.get(Calendar.HOUR_OF_DAY) - regDate.get(Calendar.HOUR_OF_DAY));
        SharedPrefHelper.setMins(profDate.get(Calendar.MINUTE) - regDate.get(Calendar.MINUTE));
        SharedPrefHelper.setSecs(profDate.get(Calendar.SECOND) - regDate.get(Calendar.SECOND));

        if (SharedPrefHelper.getYears() > 0)
            result += SharedPrefHelper.getYears() + " years ";
        if (SharedPrefHelper.getMonths() > 0)
            result += SharedPrefHelper.getMonths() + " months ";
        if (SharedPrefHelper.getDays() > 0)
            result += SharedPrefHelper.getDays() + " days ";
        if (SharedPrefHelper.getHours() > 0)
            result += SharedPrefHelper.getHours() + "hours ";
        if (SharedPrefHelper.getMins() > 0)
            result += SharedPrefHelper.getMins() + " mins ";
        if (SharedPrefHelper.getSecs() < 0)
            SharedPrefHelper.setSecs(SharedPrefHelper.getSecs()+60);
        result += SharedPrefHelper.getSecs() + " secs ";
        return result;
    }

    private void setData() {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewCurrentTime = (TextView) findViewById(R.id.textViewCurrentTime);
        textViewDiffInTime = (TextView) findViewById(R.id.textViewDiffInTime);

        SharedPrefHelper.setCurrDate(new Date());
        SharedPrefHelper.setDiffInTime(setDateDiff(SharedPrefHelper.getDate(),SharedPrefHelper.getCurrDate()));

        textViewName.setText(String.format("Name : %s", SharedPrefHelper.getName()));
        textViewAddress.setText(String.format("Address : %s", SharedPrefHelper.getAddress()));
        textViewAge.setText(String.format(getString(R.string.age), SharedPrefHelper.getAge()));
        textViewCurrentTime.setText(String.format("Current Date : %s", SharedPrefHelper.getCurrDate()));
        textViewDiffInTime.setText(String.format("Difference in Time : %s", SharedPrefHelper.getDiffInTime()));
    }
}
