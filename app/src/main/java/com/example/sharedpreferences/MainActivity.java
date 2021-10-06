package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextAge;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        intent = new Intent(getApplicationContext(), ProfileActivity.class);
        SharedPrefHelper.create(this);
        setFlag();

        readDataFromFile();
        String data = SharedPrefHelper.getData();
        if(data != null) {
            String[] arr = SharedPrefHelper.getData().split("\n");
            SharedPrefHelper.setName(arr[0]);
            SharedPrefHelper.setAddress(arr[1]);
            SharedPrefHelper.setAge(Integer.parseInt(arr[2]));
            Date date = null;
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateformat));
            try {
                date = dateFormat.parse(arr[3]);
            } catch (Exception e) { }
            SharedPrefHelper.setDate(date);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        setUpView();
        setUpListener();
    }

    private void setUpView(){
        button = findViewById(R.id.button);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextAge = findViewById(R.id.editTextAge);
    }

    private void setUpListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefHelper.setName(editTextName.getText().toString());
                SharedPrefHelper.setAddress(editTextAddress.getText().toString());
                String temp = editTextAge.getText().toString();
                boolean valid = validateTextFields(temp);
                if (valid) {
                    Date date = new Date();
                    SharedPrefHelper.setName(editTextName.getText().toString());
                    SharedPrefHelper.setAddress(editTextAddress.getText().toString());
                    SharedPrefHelper.setAge(Integer.parseInt(temp));
                    SharedPrefHelper.setDate(date);
                    SharedPrefHelper.editorApply();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (SharedPrefHelper.getFlag() == 1) {
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (SharedPrefHelper.getFlag() == 2) {
            moveTaskToBack(true);
            SharedPrefHelper.setFlag(1);
        }
    }


    private boolean validateTextFields(String temp) {
        boolean valid = true;
        if (TextUtils.isEmpty(SharedPrefHelper.getName())) {
            editTextName.setError(getString(R.string.required_field));
            valid = false;
        }
        if (TextUtils.isEmpty(SharedPrefHelper.getAddress())) {
            editTextAddress.setError(getString(R.string.required_field));
            valid = false;
        }
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

    private void setFlag(){
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists())
            SharedPrefHelper.setFlag(0);
    }

    private void readDataFromFile(){
        //Read text from file
        StringBuilder text = new StringBuilder();
        File file = new File(MainActivity.this.getFilesDir(), "text");
        try {
            File addFile = new File(file, "sample");
            BufferedReader br = new BufferedReader(new FileReader(addFile));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            System.out.println(text);
            SharedPrefHelper.setData(String.valueOf(text));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}