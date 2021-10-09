package com.example.sharedpreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.util.Date;

// Shared preference helper
public class SharedPrefHelper {

    private static SharedPreferences sharedPreferences;
    private static int flag;
    private static SharedPreferences.Editor editor;

    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String AGE = "Age";
    private static final String DATE = "Date";
    private static final String FLAG = "Flag";

    // creating the shared preference
    public static void create(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPref1", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // storing the data to shared preference
    public static void store(String name, String address, int age, int flag) {
        editor.putString(NAME, name);
        editor.putString(ADDRESS, address);
        editor.putInt(AGE, age);
        editor.putInt(FLAG, flag);
        editor.apply();
    }

    // storing the data to shared preference
    public static void store(String dateFor) {
        editor.putString(DATE, dateFor);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    // getters

    public static String getName() {
        return SharedPrefHelper.getSharedPreferences().getString(NAME, null);
    }

    public static String getAddress() {
        return SharedPrefHelper.getSharedPreferences().getString(ADDRESS, null);
    }

    public static int getAge() {
        return SharedPrefHelper.getSharedPreferences().getInt(AGE, 0);
    }

    public static Date getDate(DateFormat dateFormat) {
        Date date = null;
        try {
            date = dateFormat.parse(SharedPrefHelper.getSharedPreferences().getString(DATE, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getFlag() {
        return flag;
    }

    // setter
    public static void setFlag(int flag) {
        SharedPrefHelper.flag = flag;
    }
}
