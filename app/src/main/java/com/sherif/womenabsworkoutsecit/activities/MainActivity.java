package com.sherif.womenabsworkoutsecit.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.adapters.AllDayAdapter;
import com.sherif.womenabsworkoutsecit.adapters.WorkoutData;
import com.sherif.womenabsworkoutsecit.database.DatabaseHelper;
import com.sherif.womenabsworkoutsecit.database.DatabaseOperations;
import com.sherif.womenabsworkoutsecit.listners.RecyclerItemClickListener;
import com.sherif.womenabsworkoutsecit.newscreen.Library;
import com.sherif.womenabsworkoutsecit.receiver.NotificationReceiver;
import com.sherif.womenabsworkoutsecit.utils.AppUtils;
import com.sherif.womenabsworkoutsecit.utils.CommonMethods;
import com.sherif.womenabsworkoutsecit.utils.Constants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    public float Heightincms = 0.0f;
    public AllDayAdapter adapter;
    public ArrayList<String> arr = new ArrayList<>();
    public Context context;
    public DatabaseOperations databaseOperations;
    public TextView dayleft;
    public int daysCompletionConter = 0;
    public String exc_type;
    public RadioButton female;
    public EditText ft;
    public int height;
    public EditText inches;
    public RadioButton kg;
    public RadioButton lbs;
    public Library library;
    public SharedPreferences mSharedPreferences;
    public RadioButton male;
    public EditText months;
    public NavigationView nav_view;
    public TextView percentScore1;
    public Editor prefsEditor;
    public ProgressBar progressBar;

    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtils.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) MainActivity.this.workoutDataList.get(MainActivity.this.workoutPosition)).setProgress((float) doubleExtra);
                MainActivity.this.total_progress = 0.0d;
                int i = 0;
                MainActivity.this.daysCompletionConter = 0;
                while (true) {
                    String str = "dev";
                    if (i < Constants.TOTAL_DAYS) {
                        MainActivity mainActivity = MainActivity.this;
                        double v = mainActivity.total_progress;
                        double progress = (double) ((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress();
                        Double.isNaN(progress);
                        mainActivity.total_progress = (double) ((float) (v + ((progress * 4.348d) / 100.0d)));
                        StringBuilder sb = new StringBuilder();
                        sb.append("totalprogressreceiver");
                        sb.append(MainActivity.this.total_progress);
                        Log.i(str, sb.toString());
                        if (((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                            MainActivity.this.daysCompletionConter = MainActivity.this.daysCompletionConter + 1;
                        }
                        i++;
                    } else {
                        MainActivity mainActivity2 = MainActivity.this;
                        mainActivity2.daysCompletionConter = mainActivity2.daysCompletionConter + (MainActivity.this.daysCompletionConter / 3);
                        MainActivity.this.progressBar.setProgress((int) MainActivity.this.total_progress);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("totalprogressreceiver1");
                        sb2.append(MainActivity.this.total_progress);
                        Log.i(str, sb2.toString());
                        TextView c = MainActivity.this.percentScore1;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append((int) MainActivity.this.total_progress);
                        sb3.append("%");
                        c.setText(sb3.toString());
                        TextView d = MainActivity.this.dayleft;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Constants.TOTAL_DAYS - MainActivity.this.daysCompletionConter);
                        sb4.append(MainActivity.this.getString(R.string.dayleft));
                        d.setText(sb4.toString());
                        MainActivity.this.adapter.notifyDataSetChanged();
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(doubleExtra);
                        Log.i("progress broadcast", sb5.toString());
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public RecyclerView recyclerView;
    public double total_progress = 0.0d;
    public EditText weight;
    public int width;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;
    public EditText year;


    public int calculateBMI(float f, float f2) {
        return (int) (f2 / (f * f));
    }

    private float calculateHeightinCentimeter(float f) {
        return (float) ((int) (f * 100.0f));
    }


    public float calculateMetres(float f, float f2) {
        double d = (double) (f + (f2 / 12.0f));
        Double.isNaN(d);
        float f3 = (float) (d / 3.28d);
        this.Heightincms = calculateHeightinCentimeter(f3);
        return f3;
    }


    public float calculateweight(float f) {
        if (!this.lbs.isChecked()) {
            return f;
        }
        double d = (double) f;
        Double.isNaN(d);
        return (float) (d * 0.453592d);
    }


    public void excRepeatConfirmDialog(final int i) {
        final Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
        try {
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.repeat_confirm_addialog_layout);
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity mainActivity;
                List allDaysProgressAdv;
                TextView c2;
                String str;
                try {
                    dialog.dismiss();
                    String str2 = (String) MainActivity.this.arr.get(i);
                    if (MainActivity.this.workoutDataList != null) {
                        MainActivity.this.workoutDataList.clear();
                    }
                    if (MainActivity.this.exc_type.equalsIgnoreCase("beginner")) {
                        MainActivity.this.databaseOperations.insertExcDayData(str2, 0.0f);
                        MainActivity.this.databaseOperations.insertExcCounter(str2, 0);
                        mainActivity = MainActivity.this;
                        allDaysProgressAdv = MainActivity.this.databaseOperations.getAllDaysProgress();
                    } else {
                        MainActivity.this.databaseOperations.insertExcDayDataAdv(str2, 0.0f);
                        MainActivity.this.databaseOperations.insertExcCounterAdv(str2, 0);
                        mainActivity = MainActivity.this;
                        allDaysProgressAdv = MainActivity.this.databaseOperations.getAllDaysProgressAdv();
                    }
                    mainActivity.workoutDataList = allDaysProgressAdv;
                    MainActivity.this.adapter = new AllDayAdapter(MainActivity.this, MainActivity.this.workoutDataList);
                    MainActivity.this.recyclerView.getRecycledViewPool().clear();
                    MainActivity.this.recyclerView.setAdapter(MainActivity.this.adapter);
                    MainActivity.this.daysCompletionConter = MainActivity.this.daysCompletionConter - 1;
                    TextView d = MainActivity.this.dayleft;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Constants.TOTAL_DAYS - MainActivity.this.daysCompletionConter);
                    sb.append(MainActivity.this.getString(R.string.dayleft));
                    d.setText(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("totalprogress");
                    sb2.append(MainActivity.this.total_progress);
                    Log.i("dev", sb2.toString());
                    if (MainActivity.this.daysCompletionConter > 0) {
                        MainActivity.this.progressBar.setProgress((int) (MainActivity.this.total_progress - 4.348d));
                        c2 = MainActivity.this.percentScore1;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append((int) (MainActivity.this.total_progress - 4.348d));
                        sb3.append("%");
                        str = sb3.toString();
                    } else {
                        MainActivity.this.progressBar.setProgress(0);
                        c2 = MainActivity.this.percentScore1;
                        str = "0%";
                    }
                    c2.setText(str);
                    Intent intent = new Intent(MainActivity.this, DayActivity.class);
                    intent.putExtra("day", str2);
                    intent.putExtra("day_num", i);
                    MainActivity.this.library.saveInt("workoutPosition", Integer.valueOf(MainActivity.this.workoutPosition), MainActivity.this);
                    MainActivity.this.library.preData(str2, i, ((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress(), MainActivity.this.exc_type, MainActivity.this);
                    MainActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    private void reminderpopup() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.reminder_popup);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                MainActivity.this.nav_view.getMenu().getItem(0).setChecked(true);
            }
        });
        dialog.getWindow().setLayout(-1, -2);
        final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.datePicker1reminder);
        ((Button) dialog.findViewById(R.id.set_reminder)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int i;
                int i2;
                String str = ":";
                String str2 = "ReminderCheck";
                String str3 = "notification_minute";
                String str4 = "notification_hour";
                try {
                    dialog.dismiss();
                    if (VERSION.SDK_INT >= 23) {
                        i2 = timePicker.getHour();
                        i = timePicker.getMinute();
                    } else {
                        i2 = timePicker.getCurrentHour().intValue();
                        i = timePicker.getCurrentMinute().intValue();
                    }
                    MainActivity.this.prefsEditor.putBoolean("user_selection", true);
                    MainActivity.this.prefsEditor.putInt(str4, i2);
                    MainActivity.this.prefsEditor.putInt(str3, i);
                    Log.d(str2, "Reminder set in Main page");
                    MainActivity.this.prefsEditor.apply();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Reminder set in ");
                    sb.append(MainActivity.this.mSharedPreferences.getInt(str4, i2));
                    sb.append(str);
                    sb.append(MainActivity.this.mSharedPreferences.getInt(str3, i));
                    sb.append(str);
                    sb.append(0);
                    Log.d(str2, sb.toString());
                    MainActivity.this.setAlarm(MainActivity.this.mSharedPreferences.getInt(str4, i2), MainActivity.this.mSharedPreferences.getInt(str3, i), 0);
                    MainActivity.this.nav_view.getMenu().getItem(0).setChecked(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    public void restartDialog() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_Dialog);
        try {
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.restart_confirm_addialog_layout);
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    dialog.dismiss();
                    MainActivity.this.restartExcercise();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void shareApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"start workout in your Home. : https://play.google.com/store/apps/details?id=" +  this.getPackageName());
        intent.setType("text/plain");
        startActivity(intent);
    }
    MainActivity mainActivity2;
    Intent intent;
    public void b() {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Dialog dialog = new Dialog(this, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setLayout(-1, -1);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setFlags(1024, 1024);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.activity_customdialog_heightcheckout);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        this.lbs = (RadioButton) dialog.findViewById(R.id.lbs);
        this.kg = (RadioButton) dialog.findViewById(R.id.kg);
        this.male = (RadioButton) dialog.findViewById(R.id.male);
        this.female = (RadioButton) dialog.findViewById(R.id.female);
        this.ft = (EditText) dialog.findViewById(R.id.feet);
        this.inches = (EditText) dialog.findViewById(R.id.inches);
        this.year = (EditText) dialog.findViewById(R.id.year);
        this.months = (EditText) dialog.findViewById(R.id.month);
        this.weight = (EditText) dialog.findViewById(R.id.weight);
        ((Button) dialog.findViewById(R.id.calculate)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity mainActivity;
                int i;

                String str = "";
                if (MainActivity.this.year.getText().toString().equals(str) || MainActivity.this.months.getText().toString().equals(str) || MainActivity.this.ft.getText().toString().equals(str) || MainActivity.this.inches.getText().toString().equals(str) || MainActivity.this.weight.getText().toString().equals(str)) {
                    mainActivity = MainActivity.this;
                    i = R.string.fields;
                } else if (MainActivity.this.year.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.year.getText().toString()) < 5 || Integer.parseInt(MainActivity.this.year.getText().toString()) > 100) {
                    mainActivity = MainActivity.this;
                    i = R.string.agerange;
                } else if (MainActivity.this.months.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.months.getText().toString()) < 0 || Integer.parseInt(MainActivity.this.months.getText().toString()) > 12) {
                    mainActivity = MainActivity.this;
                    i = R.string.monthrange;
                } else if (MainActivity.this.ft.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.ft.getText().toString()) < 2 || Integer.parseInt(MainActivity.this.ft.getText().toString()) > 7) {
                    mainActivity = MainActivity.this;
                    i = R.string.feetrange;
                } else if (MainActivity.this.inches.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.inches.getText().toString()) < 0 || Integer.parseInt(MainActivity.this.inches.getText().toString()) > 12) {
                    mainActivity = MainActivity.this;
                    i = R.string.inchrange;
                } else {
                    String str2 = "HEIGHT";
                    String str3 = "BMI";
                    if (MainActivity.this.kg.isChecked()) {
                        if (MainActivity.this.weight.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.weight.getText().toString()) < 5 || Integer.parseInt(MainActivity.this.weight.getText().toString()) > 130) {
                            mainActivity = MainActivity.this;
                            i = R.string.weightrange;
                        } else {
                            MainActivity mainActivity3 = MainActivity.this;
                            float a2 = mainActivity3.calculateMetres(Float.parseFloat(mainActivity3.ft.getText().toString()), Float.parseFloat(MainActivity.this.inches.getText().toString()));
                            MainActivity mainActivity4 = MainActivity.this;
                            int b2 = mainActivity3.calculateBMI(a2, mainActivity4.calculateweight(Float.parseFloat(mainActivity4.weight.getText().toString())));
                            MainActivity mainActivity5 = MainActivity.this;
                            mainActivity5.prefsEditor = mainActivity5.mSharedPreferences.edit();
                            MainActivity.this.prefsEditor.putFloat(str3, (float) b2);
                            MainActivity.this.prefsEditor.putFloat(str2, MainActivity.this.Heightincms);
                            MainActivity.this.prefsEditor.apply();
                            mainActivity2 = MainActivity.this;
                            intent = new Intent(mainActivity2, CalculateActivity.class);
                        }
                    } else if (!MainActivity.this.lbs.isChecked()) {
                        return;
                    } else {
                        if (MainActivity.this.weight.getText().toString().matches(str) || Integer.parseInt(MainActivity.this.weight.getText().toString()) < 11 || Integer.parseInt(MainActivity.this.weight.getText().toString()) > 286) {
                            mainActivity = MainActivity.this;
                            i = R.string.weightrangelb;
                        } else {
                            MainActivity mainActivity6 = MainActivity.this;
                            float a3 = mainActivity6.calculateMetres(Float.parseFloat(mainActivity6.ft.getText().toString()), Float.parseFloat(MainActivity.this.inches.getText().toString()));
                            MainActivity mainActivity7 = MainActivity.this;
                            float b3 = (float) mainActivity6.calculateBMI(a3, mainActivity7.calculateweight(Float.parseFloat(mainActivity7.weight.getText().toString())));
                            MainActivity mainActivity8 = MainActivity.this;
                            mainActivity8.prefsEditor = mainActivity8.mSharedPreferences.edit();
                            MainActivity.this.prefsEditor.putFloat(str3, b3);
                            MainActivity.this.prefsEditor.putFloat(str2, MainActivity.this.Heightincms);
                            MainActivity.this.prefsEditor.apply();
                            mainActivity2 = MainActivity.this;
                            intent = new Intent(mainActivity2, CalculateActivity.class);
                        }
                    }
                    mainActivity2.startActivity(intent.putExtra(str3, MainActivity.this.mSharedPreferences.getFloat(str3, 0.0f)).putExtra(str2, MainActivity.this.mSharedPreferences.getFloat(str2, 0.0f)));
                    dialog.dismiss();
                    return;
                }
                Toast.makeText(mainActivity, i, Toast.LENGTH_SHORT).show();
            }
        });
        this.nav_view.getMenu().getItem(0).setChecked(true);
        dialog.show();
    }

    public boolean isRestDay(int i) {
        return i == 4;
    }



    @RequiresApi(api = 21)
    public void onCreate(Bundle bundle) {
        List<WorkoutData> allDaysProgressAdv;
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.library = new Library(this.context);
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.library.updateLocale(this.mSharedPreferences.getString("languageToLoad", ""));
        this.prefsEditor = this.mSharedPreferences.edit();
        String str = "beginner";
        this.exc_type = this.mSharedPreferences.getString("yoga_type", str);
        setContentView((int) R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.percentScore1 = (TextView) findViewById(R.id.percentScore);
        this.dayleft = (TextView) findViewById(R.id.daysLeft);
        ImageView imageView = (ImageView) findViewById(R.id.b);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler);
        if (!this.exc_type.equalsIgnoreCase(str)) {
            imageView.setBackground(getResources().getDrawable(R.mipmap.banner_111));
        }
        this.databaseOperations = new DatabaseOperations(this);
        String str2 = "thirtyday";
        boolean z = this.mSharedPreferences.getBoolean(str2, false);
        String str3 = "daysInserted";
        if (!this.mSharedPreferences.getBoolean(str3, false) && this.databaseOperations.CheckDBEmpty(DatabaseHelper.EXC_DAY_TABLE) == 0) {
            this.databaseOperations.insertExcALLDayData();
            Editor edit = this.mSharedPreferences.edit();
            edit.putBoolean(str3, true);
            edit.apply();
        }
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        if (this.exc_type.equalsIgnoreCase(str)) {
            allDaysProgressAdv = this.databaseOperations.getAllDaysProgress();
        } else {
            if (!this.databaseOperations.tableExists(DatabaseHelper.EXC_DAY_TABLE_ADVANCED)) {
                this.databaseOperations.createAdvTable();
            }
            if (this.databaseOperations.CheckDBEmpty(DatabaseHelper.EXC_DAY_TABLE_ADVANCED) == 0) {
                this.databaseOperations.insertExcALLDayDataAdv();
                Editor edit2 = this.mSharedPreferences.edit();
                edit2.putBoolean(str3, true);
                edit2.apply();
            }
            allDaysProgressAdv = this.databaseOperations.getAllDaysProgressAdv();
        }


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imageView  = (ImageView) findViewById(R.id.back_arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView reset_process = (ImageView) findViewById(R.id.reset_process);
        reset_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartDialog();
            }
        });


        this.workoutDataList = allDaysProgressAdv;
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
            double d = this.total_progress;
            double progress = (double) ((WorkoutData) this.workoutDataList.get(i)).getProgress();
            Double.isNaN(progress);
            this.total_progress = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
            if (((WorkoutData) this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                this.daysCompletionConter++;
            }
        }
        int i2 = this.daysCompletionConter;
        this.daysCompletionConter = i2 + (i2 / 3);
        this.progressBar.setProgress((int) this.total_progress);
        TextView textView = this.percentScore1;
        StringBuilder sb = new StringBuilder();
        sb.append((int) this.total_progress);
        sb.append("%");
        textView.setText(sb.toString());
        TextView textView2 = this.dayleft;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
        sb2.append(getString(R.string.dayleft));
        textView2.setText(sb2.toString());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        this.adapter = new AllDayAdapter(this, this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        for (int i3 = 1; i3 <= Constants.TOTAL_DAYS; i3++) {
            ArrayList<String> arrayList = this.arr;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Day ");
            sb3.append(i3);
            arrayList.add(sb3.toString());
        }
        if (z) {
            Editor edit3 = this.mSharedPreferences.edit();
            edit3.putBoolean(str2, false);
            edit3.apply();
            restartExcercise();
            this.daysCompletionConter = 0;
        }
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(mLayoutManager);

        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.onItemClickListener() {
            public void OnItem(View view, int i) {
                Intent intent;
                int unused = MainActivity.this.workoutPosition = i;
                if ((MainActivity.this.workoutPosition + 1) % 4 == 0) {
                    intent = new Intent(MainActivity.this, RestDayActivity.class);
                } else if (((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress() < 99.0f) {
                    intent = new Intent(MainActivity.this, DayActivity.class);
                    intent.putExtra("day", MainActivity.this.arr.get(i));
                    intent.putExtra("day_num", i);
                    intent.putExtra(NotificationCompat.CATEGORY_PROGRESS, ((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress());
                } else {
                    MainActivity.this.excRepeatConfirmDialog(i);
                    return;
                }
                MainActivity.this.startActivity(intent);
            }
        }));
//        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new onItemClickListener() {
//            public void OnItem(View view, int i) {
//                Intent intent;
//                MainActivity.this.workoutPosition = i;
//                String str = "beginner";
//                String str2 = "day";
//                if (MainActivity.this.exc_type.equalsIgnoreCase(str)) {
//                    MainActivity.this.workoutPosition = i;
//                    if ((MainActivity.this.workoutPosition + 1) % 4 == 0) {
//                        intent = new Intent(MainActivity.this, RestDayActivity.class);
//                    } else if (((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress() < 99.0f) {
//                        intent = new Intent(MainActivity.this, DayActivity.class);
//                        String str3 = (String) MainActivity.this.arr.get(i);
//                        MainActivity.this.library.saveInt(str2, Integer.valueOf(MainActivity.this.workoutPosition), MainActivity.this);
//                        MainActivity.this.library.preData(str3, MainActivity.this.workoutPosition, ((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress(), MainActivity.this.exc_type, MainActivity.this);
//
//                    }
//                    else {
//
//                        MainActivity.this.excRepeatConfirmDialog(i);
//                        return;
//                    }
//                } else if (MainActivity.this.exc_type.equalsIgnoreCase(str)) {
//                    return;
//                } else {
//                    if ((MainActivity.this.workoutPosition + 1) % 4 == 0) {
//                        intent = new Intent(MainActivity.this, RestDayActivity.class);
//                    } else if (((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress() < 99.0f) {
//                        intent = new Intent(MainActivity.this, DayActivity.class);
//                        String str32 = (String) MainActivity.this.arr.get(i);
//                        MainActivity.this.library.saveInt(str2, Integer.valueOf(MainActivity.this.workoutPosition), MainActivity.this);
//                        MainActivity.this.library.preData(str32, MainActivity.this.workoutPosition, ((WorkoutData) MainActivity.this.workoutDataList.get(i)).getProgress(), MainActivity.this.exc_type, MainActivity.this);
//                    }
//
//
//                    else {
//                        MainActivity.this.excRepeatConfirmDialog(i);
//                        return;
//                    }
//                }
//                MainActivity.this.startActivity(intent);
//            }
//        }));
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.nav_view = (NavigationView) findViewById(R.id.nav_view);
        this.nav_view.setNavigationItemSelectedListener(this);
        this.nav_view.getMenu().getItem(0).setChecked(true);
        final Menu menu = this.nav_view.getMenu();
        this.nav_view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ArrayList arrayList = new ArrayList();
                MainActivity.this.nav_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                for (int i = 0; i < menu.size(); i++) {
                    if (i == 0) {
                        menu.getItem(i).setTitle(new SpannableString(menu.getItem(i).getTitle()));
                    }
                    MainActivity.this.nav_view.findViewsWithText(arrayList, menu.getItem(i).getTitle(), View.FIND_VIEWS_WITH_TEXT);
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((TextView) it.next()).setTypeface(Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/roboto_medium.ttf"), Typeface.NORMAL);
                }
            }
        });
        if (VERSION.SDK_INT >= 23) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((NotificationManager) Objects.requireNonNull((NotificationManager) getSystemService(NotificationManager.class))).createNotificationChannel(new NotificationChannel(getString(R.string.default_notification_channel_id), getString(R.string.default_notification_channel_name), NotificationManager.IMPORTANCE_HIGH));
            }
        }
        if (getIntent().getExtras() != null) {
            for (String str4 : getIntent().getExtras().keySet()) {
                Object obj = getIntent().getExtras().get(str4);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Key: ");
                sb4.append(str4);
                sb4.append(" Value: ");
                sb4.append(obj);
                Log.d("selfie camera", sb4.toString());
            }
        }
        StrictMode.setVmPolicy(new Builder().build());
//        if (this.daysCompletionConter > 4) {
//            RateThisApp rateThisApp = new RateThisApp(this.context);
//            if (!rateThisApp.showRateUsDialog()) {
//                rateThisApp.showRateDialog(this.context, BuildConfig.APPLICATION_ID);
//            }
//        }
        Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        StringBuilder sb5 = new StringBuilder();
        String str5 = "%02d";
        sb5.append(String.format(str5, new Object[]{Integer.valueOf(instance.get(Calendar.HOUR_OF_DAY))}));
        String str6 = ":";
        sb5.append(str6);
        sb5.append(String.format(str5, new Object[]{Integer.valueOf(instance.get(Calendar.MINUTE))}));
        sb5.append(str6);
        sb5.append(String.format(str5, new Object[]{Integer.valueOf(instance.get(Calendar.SECOND))}));
        sb5.append(str6);
        sb5.append(String.format("%03d", new Object[]{Integer.valueOf(instance.get(Calendar.MILLISECOND))}));
        sb5.toString();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!Boolean.valueOf(defaultSharedPreferences.getBoolean("user_selection", false)).booleanValue()) {
            String str7 = "ReminderCheck";
            Log.d(str7, "Reminder set in Completion page");
            Editor edit4 = defaultSharedPreferences.edit();
            String str8 = "notification_hour";
            edit4.putInt(str8, instance.get(Calendar.HOUR_OF_DAY));
            String str9 = "notification_minute";
            edit4.putInt(str9, instance.get(Calendar.MINUTE));
            edit4.apply();
            new CommonMethods(this.context).setAlarm(defaultSharedPreferences.getInt(str8, instance.get(Calendar.HOUR_OF_DAY)), defaultSharedPreferences.getInt(str9, instance.get(Calendar.MINUTE)), 0);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Reminder set in ");
            sb6.append(defaultSharedPreferences.getInt(str8, instance.get(Calendar.HOUR_OF_DAY)));
            sb6.append(str6);
            sb6.append(defaultSharedPreferences.getInt(str9, instance.get(Calendar.MINUTE)));
            sb6.append(str6);
            sb6.append(0);
            Log.d(str7, sb6.toString());
        }
        registerReceiver(this.progressReceiver, new IntentFilter(AppUtils.WORKOUT_BROADCAST_FILTER));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String str = "android.intent.action.VIEW";
        int itemId = menuItem.getItemId();
        if (itemId != R.id.trainingplan) {
            if (itemId == R.id.meals_plan) {
//                startActivity(this.exc_type.equalsIgnoreCase("beginner") ? new Intent(this, MealsMainActivity.class) : new Intent(this, Mealplanadvanced.class));
//                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            } else if (itemId == R.id.reminder) {
                reminderpopup();
            } else if (itemId == R.id.restartprogress) {
                restartDialog();
            } else if (itemId == R.id.calculate) {
                b();
            } else if (itemId == R.id.share) {
                shareApp();
            } else if (itemId == R.id.rateus) {
                try {
                    startActivity(new Intent(str, Uri.parse("market://details?id=com.sherif.womenabsworkout")));
                } catch (ActivityNotFoundException unused) {
                    startActivity(new Intent(str, Uri.parse("http://play.google.com/store/apps/details?id=com.sherif.womenabsworkout")));
                }
            }
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }

    public void onResume() {
        this.nav_view.getMenu().getItem(0).setChecked(true);
        super.onResume();
    }

    public void onStart() {
        super.onStart();
    }

    public void restartExcercise() {
        Editor edit = this.mSharedPreferences.edit();
        String str = "daysInserted";
        edit.putBoolean(str, false);
        edit.apply();
        for (int i = 0; i < 30; i++) {
            String str2 = (String) this.arr.get(i);
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
        }
        edit.putBoolean(str, true);
        edit.apply();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.exc_type.equalsIgnoreCase("beginner") ? this.databaseOperations.getAllDaysProgress() : this.databaseOperations.getAllDaysProgressAdv();
        this.adapter = new AllDayAdapter(this, this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        this.recyclerView.setAdapter(this.adapter);
        this.progressBar.setProgress(0);
        this.percentScore1.setText("0%");
        TextView textView = this.dayleft;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.TOTAL_DAYS);
        sb.append(" Days left");
        textView.setText(sb.toString());
    }

    public void setAlarm(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, i);
        instance.set(Calendar.MINUTE, i2);
        instance.set(Calendar.SECOND, i3);
        ((AlarmManager) getSystemService(CATEGORY_ALARM)).setRepeating(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(getApplicationContext(), 100, new Intent(getApplicationContext(), NotificationReceiver.class), 134217728));
    }


    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            drawerLayout.closeDrawer((int) GravityCompat.START);
        }
        Intent intent = new Intent(MainActivity.this, After_Main_Activity.class);
        startActivity(intent);
        finish();
    }
}
