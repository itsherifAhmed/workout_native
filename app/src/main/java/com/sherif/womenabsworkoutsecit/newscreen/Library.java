package com.sherif.womenabsworkoutsecit.newscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

//import com.facebook.internal.NativeProtocol;

public class Library {
    public Context context;

    public Library(Context context2) {
        this.context = context2;
    }





    public boolean isConnectedToInternet() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                for (NetworkInfo state : connectivityManager.getAllNetworkInfo()) {
                    if (state.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void preData(String str, int i, float f, String str2, Context context2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context2).edit();
        edit.putString("day", str);
        edit.putInt("day_num", i);
        edit.putFloat("progress", f);
        edit.putString("yoga_type", str2);
        edit.apply();
    }

    public void saveBoolean(String str, boolean z, Context context2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context2).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public void saveFloat(String str, Float f, Context context2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context2).edit();
        edit.putFloat(str, f.floatValue());
        edit.apply();
    }

    public void saveInt(String str, Integer num, Context context2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context2).edit();
        edit.putInt(str, num.intValue());
        edit.apply();
    }

    public void saveString(String str, String str2, Context context2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context2).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public void updateLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = this.context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
