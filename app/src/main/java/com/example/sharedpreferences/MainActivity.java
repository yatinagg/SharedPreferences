package com.example.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextAge;
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
                SharedPrefHelper.store(name, address, age, System.currentTimeMillis());
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
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

}