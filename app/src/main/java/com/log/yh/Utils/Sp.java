package com.log.yh.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import de.robv.android.xposed.XSharedPreferences;

public class Sp {
    private static final String Config = "Config";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static XSharedPreferences xSharedPreferences;

    public static void SP_init(Context mcontext) {
        preferences = mcontext.getSharedPreferences(Config, Context.MODE_WORLD_READABLE);
        editor = preferences.edit();
    }

    public static void XSp_init() {
        xSharedPreferences = new XSharedPreferences("com.log.yh", Config);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void putInt(String key, Integer value) {
        editor.putInt(key, value);
        editor.apply();
    }
    public static void putBoolen(String key, Boolean value){
        editor.putBoolean(key, value);
        editor.apply();
    }
    public static String getString(String key) {
        String str=null;
        if (key.equals("title")){
            str=preferences.getString(key, "Log=\\square");
        }else if (key.equals("answer")) {
            str = preferences.getString(key, "1");
        }else if (key.equals("practice_answer")){
            str=preferences.getString(key,"1");
        }else {
            str= preferences.getString(key, "");
        }
        return str;
    }

    public static Integer getInt(String key) {
        return preferences.getInt(key, 0);
    }
    public static Boolean getBoolean(String key){
        return preferences.getBoolean(key, false);
    }
    public static String Hook_getString(String key) {
        String str=null;
        if (key.equals("title")){
            str=xSharedPreferences.getString(key, "Log=\\square");
        }else if (key.equals("answer")) {
            str = xSharedPreferences.getString(key, "1");
        }else if (key.equals("practice_answer")){
            str=xSharedPreferences.getString(key,"1");
        }else {
            str= xSharedPreferences.getString(key, "");
        }
        return str;
    }

    public static int Hook_getInt(String key) {
        return xSharedPreferences.getInt(key, 0);
    }
    public static Boolean Hook_getBoolean(String key){
        return xSharedPreferences.getBoolean(key, false);
    }
}
