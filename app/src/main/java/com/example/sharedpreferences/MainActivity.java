package com.example.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText etName;
    private EditText etAddress;
    private EditText etAge;
    private String name;
    private String address;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPrefHelper.create(this);

        age = SharedPrefHelper.getAge();
        // if there is any previous data
        if (age != 0)
            startActivity(new Intent(this, ProfileActivity.class));

        setContentView(R.layout.activity_main);
        setUpView();
        setUpListener();
    }

    // setup View
    private void setUpView() {
        button = findViewById(R.id.button_register);
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etAge = findViewById(R.id.et_age);
    }

    // setup listener
    private void setUpListener() {
        button.setOnClickListener(view -> {
            String temp = etAge.getText().toString();
            boolean valid = validateTextFields(temp);
            if (valid) {
                name = etName.getText().toString();
                address = etAddress.getText().toString();
                age = Integer.parseInt(temp);
                SharedPrefHelper.store(name, address, age, System.currentTimeMillis());
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

    // validate text fields
    private boolean validateTextFields(String temp) {
        boolean valid = true;
        if (TextUtils.isEmpty(etName.getText())) {
            etName.setError(getString(R.string.requiredField));
            valid = false;
        }
        if (TextUtils.isEmpty(etAddress.getText())) {
            etAddress.setError(getString(R.string.requiredField));
            valid = false;
        }
        if (TextUtils.isEmpty(temp)) {
            etAge.setError(getString(R.string.requiredField));
            valid = false;
        } else {
            int age = Integer.parseInt(temp);
            if (age > 100) {
                etAge.setError(getString(R.string.ageCriteria));
                valid = false;
            }
        }
        return valid;
    }

}