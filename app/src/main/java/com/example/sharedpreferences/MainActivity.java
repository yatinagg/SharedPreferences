package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextAge;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Helper.flag = 0;
        intent = new Intent(getApplicationContext(), ProfileActivity.class);
        Helper.sharedPref = getSharedPreferences("SharedPref1", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextAge = findViewById(R.id.editTextAge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.name = editTextName.getText().toString();
                Helper.address = editTextAddress.getText().toString();
                String temp = editTextAge.getText().toString();
                Boolean valid = validateTextFields(temp);
                if (valid) {
                    Helper.date = new Date();
                    SharedPreferences.Editor editor = Helper.sharedPref.edit();
                    editor.putString("name", Helper.name);
                    editor.putString("address", Helper.address);
                    editor.putInt("age", Helper.age);
                    editor.putString("date", Helper.date.toString());
                    editor.putLong("date", Helper.date.getTime());
                    editor.apply();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Helper.flag == 1) {
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (Helper.flag == 2) {
            moveTaskToBack(true);
            Helper.flag = 1;
        }
    }

    private boolean validateTextFields(String temp) {
        boolean valid = true;
        if (TextUtils.isEmpty(Helper.name)) {
            editTextName.setError("Required field");
            valid = false;
        }
        if (TextUtils.isEmpty(Helper.address)) {
            editTextAddress.setError("Required field");
            valid = false;
        }
        if (TextUtils.isEmpty(temp)) {
            editTextAge.setError("Required field");
            valid = false;
        } else {
            Helper.age = Integer.parseInt(temp);
            if (Helper.age > 100) {
                editTextAge.setError("Age shoud be less than 100");
                valid = false;
            }
        }
        return valid;
    }

}