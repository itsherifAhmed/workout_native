package com.sherif.womenabsworkoutsecit.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sherif.womenabsworkoutsecit.ConstantValues;
import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.activities.Activity_MyProfile;
import com.sherif.womenabsworkoutsecit.adapters.AllDayAdapter;
import com.sherif.womenabsworkoutsecit.adapters.WorkoutData;
import com.sherif.womenabsworkoutsecit.database.DatabaseOperations;
import com.sherif.womenabsworkoutsecit.newscreen.Library;
import com.sherif.womenabsworkoutsecit.receiver.NotificationReceiver;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;


public class ProfileFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    public static boolean isSecond = true;


    public Library f902a;
    public String b;
    public SharedPreferences c;
    public Context context;
    public int d = 25;
    public int e = 15;
    public String excercise_type;
    public SwitchCompat f;
    public NumberPicker g;
    public NumberPicker h;
    public SharedPreferences i;
    public String j;
    public String k;
    public SimpleDateFormat l;
    public SimpleDateFormat m;
    public SharedPreferences mPreferences;
    public int n;
    public Calendar o = Calendar.getInstance();
    private TextView profile,reminder,privacypolicy,reset;
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor prefsEditor;
    public ArrayList<String> arr = new ArrayList<>();

    public DatabaseOperations databaseOperations;
    public List<WorkoutData> workoutDataList;
    public String exc_type;
    public RecyclerView recyclerView;
    public AllDayAdapter adapter;
    public ProgressBar progressBar;
    public TextView percentScore1;
    public TextView dayleft;
    InterstitialAd interstitialAd;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static ProfileFragment newInstance(String str, String str2) {

        ProfileFragment mainFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    private long getTimeFromDate(int i2, int i3) {
        return (long) (((i2 * 60 * 60) + (i3 * 60)) * 1000);
    }

    private void setLayoutEnabledInit(Boolean bool) {
        isSecond = true;
    }


    public void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/html");
        intent.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=" + ProfileFragment.this.getActivity().getPackageName());
        ProfileFragment.this.startActivity(Intent.createChooser(intent, "Abs Workout For Women"));
    }

    private void updateSleepTimeEdit(Date date) {
        this.k = this.l.format(date);
        this.f902a.saveString("getSleepTime", this.k, getActivity().getApplicationContext());
    }

    private void updateWakeTimeEdit(Date date) {
        this.j = this.l.format(date);
        this.f902a.saveString("getWakeUpTime", this.j, getActivity().getApplicationContext());
    }


    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SharedPreferences.Editor edit = this.mPreferences.edit();
        if (compoundButton.getId() == R.id.sound_switch) {
            if (!z) {
                edit.putInt("sound", 0);
            } else {
                edit.putInt("sound", 1);
                Toast.makeText(getActivity(), "Sound is on!!", Toast.LENGTH_SHORT).show();
            }
        }
        edit.apply();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.l = new SimpleDateFormat("hh:mm a");
        this.m = new SimpleDateFormat("HH:mm");
        this.context = getActivity();
        this.f902a = new Library(this.context);
        this.o.set(Calendar.HOUR_OF_DAY, 10);
        this.o.set(Calendar.MINUTE, 0);
        this.o.set(Calendar.SECOND, 0);
        updateWakeTimeEdit(this.o.getTime());
        this.o.set(Calendar.HOUR_OF_DAY, 18);
        this.o.set(Calendar.MINUTE, 0);
        this.o.set(Calendar.SECOND, 0);
        updateSleepTimeEdit(this.o.getTime());
    }

    public AdRequest adRequest;
    public InterstitialAd interstitial;


    public void requestNewInterstitial() {
        this.interstitial.loadAd(this.adRequest);
    }

    private void setAdmodAds() {
        this.interstitial = new InterstitialAd(getActivity());
        this.interstitial.setAdUnitId(getString(R.string.g_inr));
        this.adRequest = new AdRequest.Builder().build();
        this.interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
        requestNewInterstitial();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("fragments", "onCreateView-frofile-frag");
        setAdmodAds();

        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.prefsEditor = this.mSharedPreferences.edit();
        this.c = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.prefsEditor = this.c.edit();
        String str = "beginner";
        this.exc_type = this.mSharedPreferences.getString("yoga_type", str);




        this.context = getActivity();
        this.f902a = new Library(this.context);
        this.b = this.c.getString("languageToLoad", "en");
        this.f902a.updateLocale(this.b);
        this.mPreferences = ((FragmentActivity) Objects.requireNonNull(getActivity())).getSharedPreferences(getResources().getString(R.string.timer_fref_file_name), 0);
        isSecond = false;
        this.n = this.mPreferences.getInt("sound", 1);
        View inflate = layoutInflater.inflate(R.layout.fragment_profile, viewGroup, false);
        this.f = (SwitchCompat) inflate.findViewById(R.id.sound_switch);
        this.f.setOnCheckedChangeListener(this);
        if (this.n == 1) {
            this.f.setChecked(true);
        } else {
            this.f.setChecked(false);
        }
        ((Toolbar) inflate.findViewById(R.id.mtoolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.getActivity().onBackPressed();
            }
        });
        this.i = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.d = this.mPreferences.getInt("resttime", 15);
        this.e = this.mPreferences.getInt("readytimer", 10);
        this.h = (NumberPicker) inflate.findViewById(R.id.restTimeNumberPicker);
        this.h.setMin(3);
        this.h.setUnit(1);
        this.h.setValue(this.d);
        this.h.setValueChangedListener(new ValueChangedListener() {
            public void valueChanged(int i, ActionEnum actionEnum) {
                SharedPreferences.Editor edit = ProfileFragment.this.mPreferences.edit();
                edit.putInt("resttime", i);
                edit.apply();
            }
        });
        this.g = (NumberPicker) inflate.findViewById(R.id.countdownNumberPicker);
        this.g.setMax(35);
        this.g.setMin(5);
        this.g.setUnit(1);
        this.g.setValue(this.e);
        this.g.setValueChangedListener(new ValueChangedListener() {
            public void valueChanged(int i, ActionEnum actionEnum) {
                SharedPreferences.Editor edit = ProfileFragment.this.mPreferences.edit();
                edit.putInt("readytimer", i);
                edit.apply();
            }
        });

        this.profile = (TextView) inflate.findViewById(R.id.myprofile);
        this.profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.startActivity(new Intent(ProfileFragment.this.getActivity(), Activity_MyProfile.class));

            }
        });
        this.reminder = (TextView) inflate.findViewById(R.id.reminder);
        this.reminder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                reminderpopup();
            }
        });
        this.privacypolicy = (TextView) inflate.findViewById(R.id.privacypolicy);
        this.privacypolicy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String url = "https://www.google.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);     }
        });

        inflate.findViewById(R.id.rateus).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    ProfileFragment.this.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.sherif.womenabsworkout")));
                } catch (ActivityNotFoundException unused) {
                    ProfileFragment.this.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=com.sherif.womenabsworkout")));
                }
            }
        });
        inflate.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProfileFragment.this.shareApp();
            }
        });
        this.reset = (TextView) inflate.findViewById(R.id.reset);

        this.reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                restartDialog();

            }
        });

        return inflate;
    }


    private void restartDialog() {
        final Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
        try {
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.restart_confirm_addialog_layout);
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {


                    dialog.dismiss();
                    SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                    edit.clear();
                    edit.commit();
                    new DatabaseOperations(getActivity()).deleteTable();
                    ProfileFragment.this.getActivity().getSharedPreferences(ConstantValues.PREFS_NAME, 0).edit().clear().commit();
                    ProfileFragment.this.getActivity().finish();
                    System.exit(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }




    private void reminderpopup() {
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.reminder_popup);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
//                ProfileFragment.this.nav_view.getMenu().getItem(0).setChecked(true);
            }
        });
        dialog.getWindow().setLayout(-1, -2);
        final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.datePicker1reminder);
        ((Button) dialog.findViewById(R.id.set_reminder)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int i;
                int i2;
                String str = ":";
                String str2 = "ReminderCheck";
                String str3 = "notification_minute";
                String str4 = "notification_hour";
                try {
                    dialog.dismiss();
                    if (Build.VERSION.SDK_INT >= 23) {
                        i2 = timePicker.getHour();
                        i = timePicker.getMinute();
                    } else {
                        i2 = timePicker.getCurrentHour().intValue();
                        i = timePicker.getCurrentMinute().intValue();
                    }
                    ProfileFragment.this.prefsEditor.putBoolean("user_selection", true);
                    ProfileFragment.this.prefsEditor.putInt(str4, i2);
                    ProfileFragment.this.prefsEditor.putInt(str3, i);
                    Log.d(str2, "Reminder set in Main page");
                    ProfileFragment.this.prefsEditor.apply();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reminder set in ");
                    sb.append(ProfileFragment.this.mSharedPreferences.getInt(str4, i2));
                    sb.append(str);
                    sb.append(ProfileFragment.this.mSharedPreferences.getInt(str3, i));
                    sb.append(str);
                    sb.append(0);
                    Log.d(str2, sb.toString());
                    ProfileFragment.this.setAlarm(ProfileFragment.this.mSharedPreferences.getInt(str4, i2), ProfileFragment.this.mSharedPreferences.getInt(str3, i), 0);
//                    ProfileFragment.this.nav_view.getMenu().getItem(0).setChecked(true);
                    Toast.makeText(getActivity(), "Reminder Set Sucsessfully!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }
    public void setAlarm(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, i);
        instance.set(Calendar.MINUTE, i2);
        instance.set(Calendar.SECOND, i3);
        ((AlarmManager) getContext().getSystemService(CATEGORY_ALARM)).setRepeating(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(getContext(), 100, new Intent(getContext(), NotificationReceiver.class), 134217728));
    }

    public void onResume() {
        super.onResume();
        Log.d("fragments", "onResume-frofile-frag");
//        Log.i(GooglePlayCallbackExtractor.BUNDLE_KEY_CALLBACK, "onResume: " + this.d + ":" + this.e);
        this.d = this.mPreferences.getInt("resttime", 25);
        this.e = this.mPreferences.getInt("readytimer", 10);
        this.h.setValue(this.d);
        this.g.setValue(this.e);
    }

    public void onStart() {
        super.onStart();
        Log.d("fragments", "onResume-frofile-frag");
//        Log.i(GooglePlayCallbackExtractor.BUNDLE_KEY_CALLBACK, "onStart: " + this.d + ":" + this.e);
        this.h.setValue(this.d);
        this.g.setValue(this.e);
    }





}
