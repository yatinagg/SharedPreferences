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
    private String name;
    private String address;
    private int age=0;
    private Date date;
    private SharedPreferences sharedPref;
    protected static int flag = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flag = 0;
        intent = new Intent(getApplicationContext(), ProfileActivity.class);
        sharedPref = getSharedPreferences("SharedPref1",MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextAge = findViewById(R.id.editTextAge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                address = editTextAddress.getText().toString();
                String temp = editTextAge.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(temp)){
                    Toast.makeText(getApplicationContext(),"Complete all the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    age = Integer.parseInt(temp);
                    date = new Date();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("name",name);
                    editor.putString("address",address);
                    editor.putInt("age",age);
                    editor.putString("date",date.toString());
                    editor.putLong("date",date.getTime());
                    editor.apply();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(flag==1){
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        }
    }

}