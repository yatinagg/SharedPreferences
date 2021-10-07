package com.example.sharedpreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedPrefHelper {

    private static SharedPreferences sharedPreferences;
    private static String name;
    private static String address;
    private static int age;
    private static Date date;
    private static Date currDate;
    private static int years;
    private static int months;
    private static int days;
    private static int hours;
    private static int mins;
    private static int secs;
    private static String diffInTime;
    private static int flag = 0;
    private static SharedPreferences.Editor editor;
    private static String data;
    private static String[] keys = {"Name","Address","Age","Date","Flag"};

    public static void create(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPref1", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    
    public static void getData(DateFormat dateFormat){
        SharedPrefHelper.setName(SharedPrefHelper.getSharedPreferences().getString(keys[0],null));
        SharedPrefHelper.setAddress(SharedPrefHelper.getSharedPreferences().getString(keys[1],null));
        SharedPrefHelper.setAge(SharedPrefHelper.getSharedPreferences().getInt(keys[2],0));
        Date date = null;
        try {
            date = dateFormat.parse(SharedPrefHelper.getSharedPreferences().getString(keys[3],null));
        } catch (Exception e) { }
        SharedPrefHelper.setDate(date);
        SharedPrefHelper.setFlag(SharedPrefHelper.getSharedPreferences().getInt(keys[4],0));
    }

    public static void store(String dateFor){
        editor.putString(keys[0],name);
        editor.putString(keys[1],address);
        editor.putInt(keys[2],age);
        editor.putString(keys[3],dateFor);
        editor.putInt(keys[4],flag);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        SharedPrefHelper.sharedPreferences = sharedPreferences;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SharedPrefHelper.name = name;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        SharedPrefHelper.address = address;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        SharedPrefHelper.age = age;
    }

    public static Date getDate() {
        return date;
    }

    public static void setDate(Date date) {
        SharedPrefHelper.date = date;
    }

    public static Date getCurrDate() {
        return currDate;
    }

    public static void setCurrDate(Date currDate) {
        SharedPrefHelper.currDate = currDate;
    }

    public static int getYears() {
        return years;
    }

    public static void setYears(int years) {
        SharedPrefHelper.years = years;
    }

    public static int getMonths() {
        return months;
    }

    public static void setMonths(int months) {
        SharedPrefHelper.months = months;
    }

    public static int getDays() {
        return days;
    }

    public static void setDays(int days) {
        SharedPrefHelper.days = days;
    }

    public static int getHours() {
        return hours;
    }

    public static void setHours(int hours) {
        SharedPrefHelper.hours = hours;
    }

    public static int getMins() {
        return mins;
    }

    public static void setMins(int mins) {
        SharedPrefHelper.mins = mins;
    }

    public static int getSecs() {
        return secs;
    }

    public static void setSecs(int secs) {
        SharedPrefHelper.secs = secs;
    }

    public static String getDiffInTime() {
        return diffInTime;
    }

    public static void setDiffInTime(String diffInTime) {
        SharedPrefHelper.diffInTime = diffInTime;
    }

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        SharedPrefHelper.flag = flag;
    }

    public static void editorApply() {
        editor.apply();
    }

    public static String getData() {
        return data;
    }

    public static void setData(String data) {
        SharedPrefHelper.data = data;
    }
}
