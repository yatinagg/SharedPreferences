package com.example.sharedpreferences;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvAge;
    private TextView tvDiffInTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupView();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvDiffInTime.setText(getString(R.string.diffInTime, dateDiff(SharedPrefHelper.getDateMilli(), System.currentTimeMillis())));
    }

    private void setupView() {
        tvName = findViewById(R.id.textViewName);
        tvAddress = findViewById(R.id.textViewAddress);
        tvAge = findViewById(R.id.textViewAge);
        tvDiffInTime = findViewById(R.id.textViewDiffInTime);
    }

    // set the data in text fields
    private void setData() {
        tvName.setText(String.format(getString(R.string.nameDisplay), SharedPrefHelper.getName()));
        tvAddress.setText(getString(R.string.address, SharedPrefHelper.getAddress()));
        tvAge.setText(getString(R.string.agePrint, SharedPrefHelper.getAge()));
        tvDiffInTime.setText(getString(R.string.diffInTime, dateDiff(SharedPrefHelper.getDateMilli(), System.currentTimeMillis())));
    }

    // calculate the date difference
    private String dateDiff(Long dateMilli, Long currDateMilli) {
        return formattedTime(currDateMilli - dateMilli);
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

}
