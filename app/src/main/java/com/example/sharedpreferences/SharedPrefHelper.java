package com.example.sharedpreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

// Shared preference helper
public class SharedPrefHelper {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String AGE = "Age";
    private static final String DATE = "Date";

    // creating the shared preference
    public static void create(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPref1", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // storing the data to shared preference
    public static void store(String name, String address, int age, Long dateMilli) {
        editor.putString(NAME, name);
        editor.putString(ADDRESS, address);
        editor.putInt(AGE, age);
        editor.putLong(DATE, dateMilli);
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

    public static Long getDateMilli() {
        return SharedPrefHelper.getSharedPreferences().getLong(DATE, 0);
    }

}
