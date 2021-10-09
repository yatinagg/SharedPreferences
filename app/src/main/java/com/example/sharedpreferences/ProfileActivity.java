package com.example.sharedpreferences;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setData();
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);
        Date date = SharedPrefHelper.getDate(dateFormat);
        String dateFor = dateFormat.format(date);
        SharedPrefHelper.setFlag(1);
        SharedPrefHelper.store(dateFor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefHelper.getFlag() == 1) {
            setContentView(R.layout.activity_profile);
            DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);
            dateDiff(SharedPrefHelper.getDate(dateFormat), new Date());
            setData();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefHelper.setFlag(2);
        this.finish();
    }

    // set the date diff
    private String dateDiff(Date date, Date currDate) {

        Calendar regDate = Calendar.getInstance();
        Calendar profDate = Calendar.getInstance();
        regDate.setTime(date);
        profDate.setTime(currDate);

        return formattedTime(profDate.getTimeInMillis() - regDate.getTimeInMillis());
    }

    // format the time in desired format
    private String formattedTime(Long milliseconds) {
        int mDay = (int) TimeUnit.MILLISECONDS.toDays(milliseconds);
        int mHr = (int) (TimeUnit.MILLISECONDS.toHours(milliseconds) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds)));
        int mMin = (int) (TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)));
        int mSec = (int) (TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        int mYear = mDay / 365;
        mDay %= 365;

        StringBuilder result = new StringBuilder();
        if (mYear != 0)
            result.append(mYear).append("years, ");
        if (mDay != 0)
            result.append(mDay).append("days, ");
        if (mHr != 0)
            result.append(mHr).append("hours, ");
        if (mMin != 0)
            result.append(mMin).append("minutes, ");
        result.append(mSec).append("seconds");
        return result.toString();
    }

    // set the data in text fields
    private void setData() {
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        TextView textViewAge = (TextView) findViewById(R.id.textViewAge);
        TextView textViewDiffInTime = (TextView) findViewById(R.id.textViewDiffInTime);

        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);

        textViewName.setText(String.format("Name : %s", SharedPrefHelper.getName()));
        textViewAddress.setText(String.format("Address : %s", SharedPrefHelper.getAddress()));
        textViewAge.setText(String.format(getString(R.string.age_print), SharedPrefHelper.getAge()));
        textViewDiffInTime.setText(String.format("Difference in Time : %s", dateDiff(SharedPrefHelper.getDate(dateFormat), new Date())));
    }
}
