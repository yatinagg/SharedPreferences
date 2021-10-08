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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPrefHelper.create(this);
        setFlag();

        String name = SharedPrefHelper.getSharedPreferences().getString(getString(R.string.name),null);
        if(name!=null){
            DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateformat), Locale.US);
            SharedPrefHelper.getData(dateFormat);
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
        button.setOnClickListener(view -> {
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
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("why","restart");
        if (SharedPrefHelper.getFlag() == 1) {
            Log.d("why","restart1");
            String name = SharedPrefHelper.getSharedPreferences().getString(getString(R.string.name),null);
            if(name!=null){
                DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateformat), Locale.US);
                SharedPrefHelper.getData(dateFormat);
            }
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
        String name = SharedPrefHelper.getSharedPreferences().getString(getString(R.string.name),null);
        if(name==null) {
            Log.d("why","null");
            SharedPrefHelper.setFlag(0);
        }
    }

}