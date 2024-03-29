package com.fitnessch19.womenabsworkout.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.adapters.WorkoutData;
import com.fitnessch19.womenabsworkout.database.DatabaseOperations;
import com.fitnessch19.womenabsworkout.newscreen.Library;
import com.fitnessch19.womenabsworkout.utils.Constants;

import java.util.List;
import java.util.Objects;

public class CompletionExcActivity extends Activity {

    /* renamed from: a  reason: collision with root package name */
    public Library f838a;
    public AdmobAds admobAdsObject = null;

    public TextView congrts_complete;
    public Context context;
    public int daysCompletionConter = 0;
    public String getYogaType;
    public int shareConter = 0;
    public TextView textViewTotExc;
    public TextView textViewTotaTime;
    public int totalExc;
    public int totalTime;
    public List<WorkoutData> workoutDataList;


    public void allCompletionDialogCreate() {
        final Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
        try {
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.restart_exc_layout);
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(CompletionExcActivity.this.getApplicationContext()).edit();
                edit.putBoolean("thirtyday", true);
                edit.apply();
                CompletionExcActivity.this.finish();
                Constants.TOTAL_DAYS = 30;
                Intent intent = new Intent(CompletionExcActivity.this, Start_Activity.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
            }
        });
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CompletionExcActivity.this, MainActivity.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void shareAllApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"start working out in your Home. : https://play.google.com/store/apps/details?id=" +  getPackageName());
        intent.setType("text/plain");
        startActivity(intent);
    }


    public void shareApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"start working out in your Home. : https://play.google.com/store/apps/details?id=" +  getPackageName());
        intent.setType("text/plain");
        startActivity(intent);
    }


    public void shareConfirmDialog() {
        final Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
        try {
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.share_app_dailog);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.gravity = 17;
            dialog.getWindow().setAttributes(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        ((Button) dialog.findViewById(R.id.shareit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                CompletionExcActivity.this.shareAllApp();
            }
        });
        ((ImageView) dialog.findViewById(R.id.close_share)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        this.shareConter++;
    }

    public void a() {
        this.admobAdsObject = new AdmobAds(this.context, (LinearLayout) findViewById(R.id.nativeAdContainer), getString(R.string.g_native));
        this.admobAdsObject.refreshAd();
    }

    public void onBackPressed() {
        if (Constants.TOTAL_DAYS - this.daysCompletionConter == 0) {
            Log.d("TAG", "daysCompletionConter on backpress if" + (Constants.TOTAL_DAYS - this.daysCompletionConter));
            allCompletionDialogCreate();
            return;
        }
        Log.d("TAG", "daysCompletionConter on backpress else" + (Constants.TOTAL_DAYS - this.daysCompletionConter));
        if (this.shareConter == 0) {
            shareConfirmDialog();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(603979776);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onCreate(@Nullable Bundle bundle) {
        StringBuilder sb;
        String str;
        String str2;
        StringBuilder sb2;
        FirebaseAnalytics firebaseAnalytics;
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.f838a = new Library(this.context);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.f838a.updateLocale(defaultSharedPreferences.getString("languageToLoad", ""));
        setContentView(R.layout.exc_completion_layout);
        this.getYogaType = defaultSharedPreferences.getString("yoga_type", "beginner");
        this.totalExc = getIntent().getIntExtra("totalExc", 0);
        this.totalTime = getIntent().getIntExtra("totalTime", 0);
        TextView imageView = (TextView) findViewById(R.id.shareimage_Congrats);
        this.textViewTotaTime = (TextView) findViewById(R.id.congrts_duration);
        this.textViewTotExc = (TextView) findViewById(R.id.congrts_ExNo);
        this.congrts_complete = (TextView) findViewById(R.id.congrts_complete);

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        Gson gson = new Gson();
        String string = sharedPreferences.getString("json_string", "");


        int i = this.totalTime;
        int i2 = i / 60;
        int i3 = i % 60;
        if (i2 < 10) {
            sb = new StringBuilder();
            sb.append("0");
        } else {
            sb = new StringBuilder();
            sb.append("");
        }
        sb.append(i2);
        String sb3 = sb.toString();
        if (i3 < 10) {
            str = "0" + i3;
        } else {
            str = "" + i3;
        }
        this.textViewTotaTime.setText(sb3 + ":" + str);
        this.textViewTotExc.setText("" + this.totalExc);
        String str3 = ((Bundle) Objects.requireNonNull(getIntent().getExtras())).getString("day").split(" ")[1];
        this.congrts_complete.setText(getResources().getString(R.string.day) + " " + str3 + " " + getResources().getString(R.string.completed));
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompletionExcActivity.this.shareApp();
            }
        });
        findViewById(R.id.closeimage_Congrats).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Constants.TOTAL_DAYS - CompletionExcActivity.this.daysCompletionConter == 0) {
                    Log.d("TAG", "daysCompletionConter onCreate" + (Constants.TOTAL_DAYS - CompletionExcActivity.this.daysCompletionConter));
                    CompletionExcActivity.this.allCompletionDialogCreate();
                    return;
                }
                Log.d("TAG", "daysCompletionConter onCreate" + (Constants.TOTAL_DAYS - CompletionExcActivity.this.daysCompletionConter));
                if (CompletionExcActivity.this.shareConter == 0) {
                    CompletionExcActivity.this.shareConfirmDialog();
                    return;
                }
                Intent intent = new Intent(CompletionExcActivity.this, MainActivity.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
                CompletionExcActivity.this.onBackPressed();
            }
        });


        a();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = new DatabaseOperations(this).getAllDaysProgress();
        for (int i4 = 0; i4 < Constants.TOTAL_DAYS; i4++) {
            if (this.workoutDataList.get(i4).getProgress() >= 99.0f) {
                this.daysCompletionConter++;
            }
        }
        int i5 = this.daysCompletionConter;
        this.daysCompletionConter = i5 + (i5 / 3);
        if (this.daysCompletionConter % 5 == 0) {
            Log.i("debashish", "day completion" + this.daysCompletionConter);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("open_time", this.daysCompletionConter);
            if (this.getYogaType.equalsIgnoreCase("beginner")) {
                firebaseAnalytics = FirebaseAnalytics.getInstance(this);
                sb2 = new StringBuilder();
                str2 = "Days_completed_beginner_";
            } else {
                firebaseAnalytics = FirebaseAnalytics.getInstance(this);
                sb2 = new StringBuilder();
                str2 = "Days_completed_advanced_";
            }
            sb2.append(str2);
            sb2.append(this.daysCompletionConter);
            firebaseAnalytics.logEvent(sb2.toString(), bundle2);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
