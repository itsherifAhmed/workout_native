package com.sherif.womenabsworkoutsecit.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sherif.womenabsworkoutsecit.GifImageView;
import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.fragments.Calculate_Fragment;
import com.sherif.womenabsworkoutsecit.fragments.MealPlanFragment;
import com.sherif.womenabsworkoutsecit.fragments.ProfileFragment;
import com.sherif.womenabsworkoutsecit.fragments.WorkoutFragment;
import com.sherif.womenabsworkoutsecit.newscreen.Library;
import com.sherif.womenabsworkoutsecit.receiver.NotificationReceiver;

import java.util.Calendar;

public class After_Main_Activity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    public Context context;
    public Fragment fragment;
    public int hour;
    public Library k;
    public String l;
    public SharedPreferences mSharedPreferences;
    public BottomNavigationMenuView menuView;
    public int minute;
    public View notificationBadge;
    public CoordinatorLayout notificationlayout;
    public boolean previouslyStarted;


    public void onBackPressed() {
        this.bottomNavigationView.setVisibility(View.VISIBLE);
        if (!Boolean.valueOf(this.mSharedPreferences.getBoolean("user_selection", false)).booleanValue()) {
            setAlarm(this.mSharedPreferences.getInt("notification_hour", this.hour), this.mSharedPreferences.getInt("notification_minute", this.minute), 0);
        }
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.adview_layout_exit);
        ((GifImageView) dialog.findViewById(R.id.GifImageView)).setGifImageResource(R.drawable.rate);
        ((Button) dialog.findViewById(R.id.btnno)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.btnrate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {

                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        ((Button) dialog.findViewById(R.id.btnyes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                System.exit(1);


            }
        });
        dialog.show();


    }

    public void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
//        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.context = this;
        this.k = new Library(this.context);
        this.l = this.mSharedPreferences.getString("languageToLoad", "en");
        this.k.updateLocale(this.l);
        requestWindowFeature(1);
        setContentView((int) R.layout.after_activity_main);


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        String str2 = "";
        openFragment(WorkoutFragment.newInstance(str2, str2, this));
        this.menuView = (BottomNavigationMenuView) this.bottomNavigationView.getChildAt(0);
//        final BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) this.menuView.getChildAt(1);
//        this.notificationBadge = LayoutInflater.from(this).inflate(R.layout.badge, this.menuView, false);
//        if (!AppUtils.ifTipWatched) {
//            bottomNavigationItemView.addView(this.notificationBadge);
//        }
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                String str = "";
                switch (menuItem.getItemId()) {
                    case R.id.training:


//                        toolbar.setTitle(getString(R.string.app_name));
                        openFragment(WorkoutFragment.newInstance(str, str, After_Main_Activity.this));
                        return true;

                    case R.id.calculator:
//                        toolbar.setTitle("BMI Calculator");
                        openFragment(Calculate_Fragment.newInstance(str, str));
                        return true;

                    case R.id.meal_plan:
//                        toolbar.setTitle("Diet Plan");
                        openFragment(MealPlanFragment.newInstance(str, str));
                        return true;

                    case R.id.profile:
//                        toolbar.setTitle("Me");
//                    Intent intent = new Intent(After_MainActivity.this, Activity_MyProfile.class);
//                    startActivity(intent);
                        openFragment(ProfileFragment.newInstance(str, str));
                        return true;


                    default:

                        return false;

                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.ex_time_toolbar);
        this.notificationlayout = (CoordinatorLayout) findViewById(R.id.notification_layout);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        Button button = (Button) findViewById(R.id.set);
        final SharedPreferences.Editor edit = this.mSharedPreferences.edit();
        if (Build.VERSION.SDK_INT >= 23) {
            this.hour = timePicker.getHour();
            i = timePicker.getMinute();
        } else {
            this.hour = timePicker.getCurrentHour().intValue();
            i = timePicker.getCurrentMinute().intValue();
        }
        this.minute = i;
        edit.putInt("notification_hour", this.hour);
        edit.putInt("notification_minute", this.minute);
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        this.previouslyStarted = this.mSharedPreferences.getBoolean("first_time_notification", false);
        if (!this.previouslyStarted) {
            SharedPreferences.Editor edit2 = this.mSharedPreferences.edit();
            edit2.putBoolean("first_time_notification", Boolean.TRUE.booleanValue());
            edit2.apply();
            this.notificationlayout.setVisibility(View.VISIBLE);
        } else {
            this.bottomNavigationView.setVisibility(View.VISIBLE);
            this.notificationlayout.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                After_Main_Activity welcomeBanner;
                int i;
                if (Build.VERSION.SDK_INT >= 23) {
                    int unused = After_Main_Activity.this.hour = timePicker.getHour();
                    welcomeBanner = After_Main_Activity.this;
                    i = timePicker.getMinute();
                } else {
                    int unused2 = After_Main_Activity.this.hour = timePicker.getCurrentHour().intValue();
                    welcomeBanner = After_Main_Activity.this;
                    i = timePicker.getCurrentMinute().intValue();
                }
                int unused3 = welcomeBanner.minute = i;
                edit.putBoolean("user_selection", true);
                edit.putInt("notification_hour", After_Main_Activity.this.hour);
                edit.putInt("notification_minute", After_Main_Activity.this.minute);
                Log.d("ReminderCheck", "Reminder set in Main page");
                edit.apply();
                Log.d("ReminderCheck", "Reminder set in " + After_Main_Activity.this.mSharedPreferences.getInt("notification_hour", After_Main_Activity.this.hour) + ":" + After_Main_Activity.this.mSharedPreferences.getInt("notification_minute", After_Main_Activity.this.minute) + ":" + 0);
                After_Main_Activity welcomeBanner2 = After_Main_Activity.this;
                welcomeBanner2.setAlarm(welcomeBanner2.mSharedPreferences.getInt("notification_hour", After_Main_Activity.this.hour), After_Main_Activity.this.mSharedPreferences.getInt("notification_minute", After_Main_Activity.this.minute), 0);
                After_Main_Activity.this.notificationlayout.setVisibility(View.GONE);
                After_Main_Activity.this.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                After_Main_Activity welcomeBanner = After_Main_Activity.this;
                welcomeBanner.setAlarm(welcomeBanner.mSharedPreferences.getInt("notification_hour", After_Main_Activity.this.hour), After_Main_Activity.this.mSharedPreferences.getInt("notification_minute", After_Main_Activity.this.minute), 0);
                After_Main_Activity.this.notificationlayout.setVisibility(View.GONE);
                After_Main_Activity.this.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public void loadFragment_Calculator(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
//        toolbar.setTitle("BMI Calculator");
        bottomNavigationView.setSelectedItemId(R.id.calculator);
    }

    public void loadFragment_Mealplan(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
//        toolbar.setTitle("Diet Plan");
        bottomNavigationView.setSelectedItemId(R.id.meal_plan);
    }

    public void onResume() {
        super.onResume();
    }

    @SuppressLint("WrongConstant")
    public void setAlarm(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, i3);
        ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(getApplicationContext(), 100, new Intent(getApplicationContext(), NotificationReceiver.class), 134217728));
    }
}
