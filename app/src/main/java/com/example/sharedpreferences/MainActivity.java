package com.example.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextAge;
    private String name;
    private String address;
    private int age;
    private Date date;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPrefHelper.create(this);
        setFlag();

        age = SharedPrefHelper.getAge();
        // if there is any previous data
        if (age != 0) {
            DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);
            address = SharedPrefHelper.getAddress();
            age = SharedPrefHelper.getAge();
            date = SharedPrefHelper.getDate(dateFormat);
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        setContentView(R.layout.activity_main);
        setUpView();
        setUpListener();
    }

    // setup View
    private void setUpView() {
        button = findViewById(R.id.button);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextAge = findViewById(R.id.editTextAge);
    }

    // setup listener
    private void setUpListener() {
        button.setOnClickListener(view -> {
            String temp = editTextAge.getText().toString();
            boolean valid = validateTextFields(temp);
            if (valid) {
                name = editTextName.getText().toString();
                address = editTextAddress.getText().toString();
                age = Integer.parseInt(temp);
                date = new Date();
                SharedPrefHelper.store(name, address, age, flag);
                DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);
                String dateFor = dateFormat.format(date);
                SharedPrefHelper.store(dateFor);
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (SharedPrefHelper.getFlag() == 1) {
            age = SharedPrefHelper.getAge();
            if (age != 0) {
                DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat), Locale.US);
                date = SharedPrefHelper.getDate(dateFormat);
            }
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        } else if (SharedPrefHelper.getFlag() == 2) {
            moveTaskToBack(true);
            SharedPrefHelper.setFlag(1);
        }
    }


    // validate text fields
    private boolean validateTextFields(String temp) {
        boolean valid = true;
        if (TextUtils.isEmpty(temp)) {
            editTextAge.setError(getString(R.string.required_field));
            valid = false;
        } else {
            int age = Integer.parseInt(temp);
            if (age > 100) {
                editTextAge.setError(getString(R.string.age_criteria));
                valid = false;
            }
        }
        return valid;
    }

    // set flag
    private void setFlag() {
        String name = SharedPrefHelper.getName();
        if (name == null) {
            Log.d("why", "null");
            SharedPrefHelper.setFlag(0);
        }
        flag = SharedPrefHelper.getFlag();
    }

}