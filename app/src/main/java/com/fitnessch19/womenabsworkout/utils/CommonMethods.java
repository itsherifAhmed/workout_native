package com.fitnessch19.womenabsworkout.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.fitnessch19.womenabsworkout.receiver.NotificationReceiver;

import java.util.Calendar;
import java.util.Locale;

public class CommonMethods {
    public Context context;

    public CommonMethods(Context context2) {
        this.context = context2;
    }

    public void setAlarm(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, i3);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.context.getApplicationContext(), 100, new Intent(this.context.getApplicationContext(), NotificationReceiver.class), 134217728);
        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager != null) {
            alarmManager.setRepeating(0, instance.getTimeInMillis(), 86400000, broadcast);
        }
    }
    public void a(AppCompatActivity appCompatActivity) {
        appCompatActivity.requestWindowFeature(1);

    }

    public void a(String str) {
        if (a()) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                this.context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this.context, "Please Check Internet Connection..", 0).show();
        }
    }

    public boolean a() {
        NetworkInfo[] allNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
            if (!(connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null)) {
                for (NetworkInfo state : allNetworkInfo) {
                    if (state.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void b(String str) {
        Locale locale = new Locale(str);
        Resources resources = this.context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }


}
