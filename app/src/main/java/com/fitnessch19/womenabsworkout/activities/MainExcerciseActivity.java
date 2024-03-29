package com.fitnessch19.womenabsworkout.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.a.a.d;
import com.fitnessch19.womenabsworkout.a.a.f;
import com.fitnessch19.womenabsworkout.a.a.g;
import com.fitnessch19.womenabsworkout.a.a.h;
import com.fitnessch19.womenabsworkout.a.a.i;
import com.fitnessch19.womenabsworkout.a.a.j;
import com.fitnessch19.womenabsworkout.a.a.k;
import com.fitnessch19.womenabsworkout.a.a.l;
import com.fitnessch19.womenabsworkout.a.a.m;
import com.fitnessch19.womenabsworkout.a.a.n;
import com.fitnessch19.womenabsworkout.a.a.o;
import com.fitnessch19.womenabsworkout.a.a.p;
import com.fitnessch19.womenabsworkout.a.a.q;
import com.fitnessch19.womenabsworkout.a.a.r;
import com.fitnessch19.womenabsworkout.a.a.s;
import com.fitnessch19.womenabsworkout.a.a.t;
import com.fitnessch19.womenabsworkout.adapters.WorkoutData;
import com.fitnessch19.womenabsworkout.database.DatabaseOperations;
import com.fitnessch19.womenabsworkout.newscreen.Library;
import com.fitnessch19.womenabsworkout.utils.AbsWomenApplication;
import com.fitnessch19.womenabsworkout.utils.AppUtils;
import com.fitnessch19.womenabsworkout.utils.Constants;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class MainExcerciseActivity extends AppCompatActivity {
    public int A;
    public boolean B = false;
    public boolean C;
    public boolean D;
    public boolean E;
    public boolean F;
    public String[] G;
    public String H;
    public Toolbar I;

    public Library K;
    public Boolean L = false;
    public Boolean M = false;
    public long REST_TIME_IN_MS = 25000;
    public FAImageView animImageFull;

    public Context context;
    public TextView count;
    public TextView countRestTimer;
    public DatabaseOperations databaseOperations;
    public String day;
    public int excCouner;
    public TextView excDescInReadyToGo;
    public long excDurGlobal;
    public TextView excName;
    public TextView excNameInReadyToGo;
    public CountDownTimer excersiseTimer;
    public int i = 0;
    public int k;
    public int l;
    public LinearLayout layoutprogress;
    public AbsWomenApplication m;
    public int mainExcCounter = 1;
    public float mainExcProgress = 1.0f;
    public int n;
    public FAImageView nextExerciseAnimation;
    public TextView nextExerciseCycles;
    public TextView nextExerciseName;
    public int o;
    public InterstitialAd p;
    public String packageName;
    public ImageView pauseMainExcersise;
    public ImageView pauseRestTime;
    public ImageView playPause;
    public double progress;
    public RoundCornerProgressBar progressbar;
    public InterstitialAd q;
    public AdRequest r;
    public CountDownTimer readyToGoTimer;
    public CoordinatorLayout readytogo_layout;
    public CoordinatorLayout restScreen;
    public CountDownTimer restTimer;
    public ProgressBar restTimerprogress;
    public ImageView resumRestTime;
    public ImageView resumeMainExcersise;
    public AdRequest s;
    public long s1;
    public AdmobAds t;
    public TextView timerTop;
    public ProgressBar timerprogress;
    public ProgressBar topProgressBar;
    public TextView tvProgress;
    public TextView tvProgressMax;
    public String u;
    public int v;
    public String w;
    public ArrayList<WorkoutData> workoutDataList;
    public NumberPicker x;
    public SharedPreferences y;
    public String z;

    public static /* synthetic */ int b(MainExcerciseActivity mainExcerciseActivity) {
        int i2 = mainExcerciseActivity.excCouner;
        mainExcerciseActivity.excCouner = i2 + 1;
        return i2;
    }

    private void excinfo() {
        Dialog dialog = new Dialog(this.context, R.style.AppTheme);
        try {
            ((Window) Objects.requireNonNull(dialog.getWindow())).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.activity_excinfodialog);
        ((Window) Objects.requireNonNull(dialog.getWindow())).setLayout(-1, -1);
        dialog.setCancelable(true);
        dialog.getWindow().setFlags(1024, 1024);
        FAImageView fAImageView = (FAImageView) dialog.findViewById(R.id.animation_exDetail);
        TextView textView = (TextView) dialog.findViewById(R.id.description_exDetail);
        ((TextView) dialog.findViewById(R.id.dialogexcName)).setText(this.z);
        fAImageView.setInterval(1000);
        fAImageView.setLoop(true);
        fAImageView.reset();
        for (int addImageFrame : this.workoutDataList.get(this.excCouner).getImageIdList()) {
            fAImageView.addImageFrame(addImageFrame);
        }
        fAImageView.startAnimation();
        this.A = this.workoutDataList.get(this.excCouner).getExcDescResId();
        textView.setText(this.A);
        dialog.show();
    }

    private void exitConfirmDialog(boolean z2) {
        Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
        try {
            ((Window) Objects.requireNonNull(dialog.getWindow())).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setContentView(R.layout.exit_confirm_addialog_layout);
        ((Window) Objects.requireNonNull(dialog.getWindow())).setLayout(-1, -2);
        dialog.setCancelable(true);
        this.G = new String[]{getResources().getString(R.string.quit1), getResources().getString(R.string.quit2), getResources().getString(R.string.quit3), getResources().getString(R.string.quit4), getResources().getString(R.string.quit5)};
        this.H = this.G[new Random().nextInt(this.G.length)];
        ((TextView) dialog.findViewById(R.id.quit_text)).setText(this.H);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new m(this, dialog));
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new o(dialog));
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.layoutContainer_dialog);
        if (z2) {
            this.t.displayAdmobAdOnLoad_Dialog(linearLayout);
        }
        dialog.show();
    }

    private void getScreenHeightWidth() {
        this.context = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.k = displayMetrics.heightPixels;
        this.l = displayMetrics.widthPixels;
    }


    public void mainExcTimer(long j, int i2, float f) {
        this.animImageFull.reset();
        for (int addImageFrame : this.workoutDataList.get(this.excCouner).getImageIdList()) {
            this.animImageFull.addImageFrame(addImageFrame);
        }
        this.restScreen.setVisibility(View.GONE);
        this.animImageFull.startAnimation();
        Log.i("setMax", "progressbar: " + (this.excDurGlobal / 1000));
        this.progressbar.setMax((float) ((this.excDurGlobal / 1000) - 1));
        this.topProgressBar = (ProgressBar) this.layoutprogress.findViewById(this.excCouner);
        this.topProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        this.topProgressBar.setMax((((int) this.excDurGlobal) / 1000) - 1);
        this.m.addEarCorn(this.v);
        Log.i("mainExcTimer", "setMax: " + (j / 1000));
        final float f2 = f;
        final int i3 = i2;
        this.excersiseTimer = new CountDownTimer(j, 1000) {

            /* renamed from: a  reason: collision with root package name */
            public float f863a = f2;
            public int b = i3;
            public int c;

            @SuppressLint({"SetTextI18n"})
            public void onFinish() {
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.C = false;
                RoundCornerProgressBar x = mainExcerciseActivity.progressbar;
                float f2 = this.f863a;
                this.f863a = f2 + 1.0f;
                x.setProgress(f2);
                Log.i("onTick onFinish", "count: " + this.b + "f " + this.f863a);
                MainExcerciseActivity.this.topProgressBar.setProgress((((int) MainExcerciseActivity.this.excDurGlobal) / 1000) - 1);
                MainExcerciseActivity.b(MainExcerciseActivity.this);
                MainExcerciseActivity.this.animImageFull.stopAnimation();
                if (MainExcerciseActivity.this.excCouner < MainExcerciseActivity.this.workoutDataList.size()) {
                    MainExcerciseActivity.this.restScreen.setVisibility(View.VISIBLE);
                    MainExcerciseActivity.this.progressbar.setMax((float) ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles());
                    TextView n = MainExcerciseActivity.this.tvProgress;
                    StringBuilder sb = new StringBuilder();
                    int i = this.b;
                    this.b = i + 1;
                    sb.append(i);
                    sb.append("");
                    n.setText(sb.toString());
                    TextView B = MainExcerciseActivity.this.tvProgressMax;
                    B.setText(((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles() + "");
                    this.f863a = 1.0f;
                    this.b = 1;
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    double size = (double) ((float) mainExcerciseActivity2.workoutDataList.size());
                    Double.isNaN(size);
                    double unused = mainExcerciseActivity2.progress = 100.0d / size;
                    Log.i("MainExcerciseActivity", "progress: " + MainExcerciseActivity.this.progress);
                    if (MainExcerciseActivity.this.u.equalsIgnoreCase("beginner")) {
                        float excDayProgress = MainExcerciseActivity.this.databaseOperations.getExcDayProgress(MainExcerciseActivity.this.day);
                        MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                        double d2 = (double) excDayProgress;
                        double h = mainExcerciseActivity3.progress;
                        Double.isNaN(d2);
                        double unused2 = mainExcerciseActivity3.progress = d2 + h;
                        if (MainExcerciseActivity.this.progress <= 100.0d) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayData(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                        }
                        if (MainExcerciseActivity.this.excCouner <= MainExcerciseActivity.this.workoutDataList.size()) {
                            MainExcerciseActivity.this.databaseOperations.insertExcCounter(MainExcerciseActivity.this.day, MainExcerciseActivity.this.excCouner);
                        }
                    } else {
                        float excDayProgressAdv = MainExcerciseActivity.this.databaseOperations.getExcDayProgressAdv(MainExcerciseActivity.this.day);
                        MainExcerciseActivity mainExcerciseActivity4 = MainExcerciseActivity.this;
                        double d3 = (double) excDayProgressAdv;
                        double h2 = mainExcerciseActivity4.progress;
                        Double.isNaN(d3);
                        double unused3 = mainExcerciseActivity4.progress = d3 + h2;
                        if (MainExcerciseActivity.this.progress <= 100.0d) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayDataAdv(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                        }
                        if (MainExcerciseActivity.this.excCouner <= MainExcerciseActivity.this.workoutDataList.size()) {
                            MainExcerciseActivity.this.databaseOperations.insertExcCounterAdv(MainExcerciseActivity.this.day, MainExcerciseActivity.this.excCouner);
                        }
                    }
                    try {
                        Intent intent = new Intent(AppUtils.WORKOUT_BROADCAST_FILTER);
                        intent.putExtra(AppUtils.KEY_PROGRESS, MainExcerciseActivity.this.progress);
                        MainExcerciseActivity.this.sendBroadcast(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e("Failed update progress", e2.getMessage());
                    }
                    MainExcerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                    MainExcerciseActivity.this.resumRestTime.setVisibility(View.GONE);
                    MainExcerciseActivity mainExcerciseActivity5 = MainExcerciseActivity.this;
                    if (!mainExcerciseActivity5.D) {
                        mainExcerciseActivity5.a(mainExcerciseActivity5.REST_TIME_IN_MS);
                    }
                } else {
                    MainExcerciseActivity mainExcerciseActivity6 = MainExcerciseActivity.this;
                    double size2 = (double) ((float) mainExcerciseActivity6.workoutDataList.size());
                    Double.isNaN(size2);
                    double unused4 = mainExcerciseActivity6.progress = 100.0d / size2;
                    if (MainExcerciseActivity.this.u.equalsIgnoreCase("beginner")) {
                        float excDayProgress2 = MainExcerciseActivity.this.databaseOperations.getExcDayProgress(MainExcerciseActivity.this.day);
                        MainExcerciseActivity mainExcerciseActivity7 = MainExcerciseActivity.this;
                        double d4 = (double) excDayProgress2;
                        double h3 = mainExcerciseActivity7.progress;
                        Double.isNaN(d4);
                        double unused5 = mainExcerciseActivity7.progress = d4 + h3;
                        if (((int) MainExcerciseActivity.this.progress) <= 100 && MainExcerciseActivity.this.progress >= 98.0d) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayData(MainExcerciseActivity.this.day, 100.0f);
                        } else if (((int) MainExcerciseActivity.this.progress) <= 100) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayData(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                        }
                        if (MainExcerciseActivity.this.excCouner <= MainExcerciseActivity.this.workoutDataList.size()) {
                            MainExcerciseActivity.this.databaseOperations.insertExcCounter(MainExcerciseActivity.this.day, MainExcerciseActivity.this.excCouner);
                        }
                    } else {
                        float excDayProgressAdv2 = MainExcerciseActivity.this.databaseOperations.getExcDayProgressAdv(MainExcerciseActivity.this.day);
                        MainExcerciseActivity mainExcerciseActivity8 = MainExcerciseActivity.this;
                        double d5 = (double) excDayProgressAdv2;
                        double h4 = mainExcerciseActivity8.progress;
                        Double.isNaN(d5);
                        double unused6 = mainExcerciseActivity8.progress = d5 + h4;
                        if (((int) MainExcerciseActivity.this.progress) <= 100 && MainExcerciseActivity.this.progress >= 98.0d) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayDataAdv(MainExcerciseActivity.this.day, 100.0f);
                        } else if (((int) MainExcerciseActivity.this.progress) <= 100) {
                            MainExcerciseActivity.this.databaseOperations.insertExcDayDataAdv(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                        }
                        if (MainExcerciseActivity.this.excCouner <= MainExcerciseActivity.this.workoutDataList.size()) {
                            MainExcerciseActivity.this.databaseOperations.insertExcCounterAdv(MainExcerciseActivity.this.day, MainExcerciseActivity.this.excCouner);
                        }
                    }
                    try {
                        Intent intent2 = new Intent(AppUtils.WORKOUT_BROADCAST_FILTER);
                        if (MainExcerciseActivity.this.progress >= 98.0d) {
                            intent2.putExtra(AppUtils.KEY_PROGRESS, 100.0d);
                        } else {
                            intent2.putExtra(AppUtils.KEY_PROGRESS, MainExcerciseActivity.this.progress);
                        }
                        MainExcerciseActivity.this.sendBroadcast(intent2);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    MainExcerciseActivity.this.restScreen.setVisibility(View.GONE);
                    Intent intent3 = new Intent(MainExcerciseActivity.this, CompletionExcActivity.class);
                    intent3.putExtra("day", MainExcerciseActivity.this.day);
                    int i2 = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < MainExcerciseActivity.this.workoutDataList.size(); i4++) {
                        i2 += ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(i4)).getExcCycles();
                        Log.d("TAG", "totalExc MainExceActivity " + i2);
                        i3 = i3 + ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(i4)).getImageIdList().length + Constants.REST_TIME;
                    }
                    intent3.putExtra("totalExc", i2);
                    intent3.putExtra("totalTime", i3);
                    MainExcerciseActivity.this.startActivity(intent3);
                    if (MainExcerciseActivity.this.p.isLoaded()) {
                        MainExcerciseActivity.this.p.show();
                    }
                }
                Log.i("MainExcerciseActivity", "excCouner: " + MainExcerciseActivity.this.excCouner);
                float unused7 = MainExcerciseActivity.this.mainExcProgress = 1.0f;
                int unused8 = MainExcerciseActivity.this.mainExcCounter = 1;
            }

            /* JADX WARNING: Removed duplicated region for block: B:21:0x0170 A[SYNTHETIC, Splitter:B:21:0x0170] */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x01a4 A[Catch:{ Exception -> 0x01d0 }] */
            /* JADX WARNING: Removed duplicated region for block: B:29:0x0219  */
            /* JADX WARNING: Removed duplicated region for block: B:30:0x0223  */
            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                int i;
                int i2;
                StringBuilder sb;
                AbsWomenApplication absWomenApplication;
                int i3 = 0;
                TextView B = null;
                String sb2 = null;
                TextView n = null;
                String sb3 = null;
                String str = "";
                StringBuilder sb4 = new StringBuilder();
                String str2 = "millisUntilFinished: ";
                sb4.append(str2);
                sb4.append(j);
                Log.i("millisUntilFinished", sb4.toString());
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.C = true;
                mainExcerciseActivity.E = false;
                try {
                    if (((WorkoutData) mainExcerciseActivity.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length <= 2) {
                        if (this.b <= ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) {
                            n = MainExcerciseActivity.this.tvProgress;
                            StringBuilder sb5 = new StringBuilder();
                            int i4 = this.b;
                            this.b = i4 + 1;
                            sb5.append(i4);
                            sb5.append(str);
                            sb3 = sb5.toString();
                        }
                        RoundCornerProgressBar x = MainExcerciseActivity.this.progressbar;
                        float f2 = this.f863a;
                        this.f863a = 1.0f + f2;
                        x.setProgress(f2);
                        MainExcerciseActivity.this.topProgressBar.setProgress((int) this.f863a);
                        TextView z = MainExcerciseActivity.this.timerTop;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(this.b);
                        sb6.append(str);
                        z.setText(sb6.toString());
                        MainExcerciseActivity.this.z = ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", " ");
                        MainExcerciseActivity.this.z = MainExcerciseActivity.this.z.toUpperCase();
                        MainExcerciseActivity.this.excName.setText(MainExcerciseActivity.this.z);
                        String str3 = "/";
                        if (MainExcerciseActivity.this.z.equalsIgnoreCase("plank")) {
                        }
                        B.setText(sb2);
                        MainExcerciseActivity.this.s1 = j;
                        MainExcerciseActivity.this.mainExcCounter = this.b;
                        MainExcerciseActivity.this.mainExcProgress = this.f863a;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("onTick: ");
                        sb7.append(this.b);
                        sb7.append("      ");
                        sb7.append(this.c);
                        sb7.append("           ");
                        sb7.append(i3);
                        Log.d("mycount", sb7.toString());
                        i = this.c;
                        i2 = this.b;
                        if (i == i2) {
                        }
                    } else if (this.f863a == 1.0f) {
                        n = MainExcerciseActivity.this.tvProgress;
                        sb3 = "1";
                    } else {
                        if (this.f863a % ((float) ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length) == 0.0f) {
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(str2);
                            sb8.append(this.f863a % ((float) ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length));
                            Log.i("mainExce", sb8.toString());
                            n = MainExcerciseActivity.this.tvProgress;
                            StringBuilder sb9 = new StringBuilder();
                            int i5 = this.b;
                            this.b = i5 + 1;
                            sb9.append(i5);
                            sb9.append(str);
                            sb3 = sb9.toString();
                        }
                        RoundCornerProgressBar x2 = MainExcerciseActivity.this.progressbar;
                        float f22 = this.f863a;
                        this.f863a = 1.0f + f22;
                        x2.setProgress(f22);
                        MainExcerciseActivity.this.topProgressBar.setProgress((int) this.f863a);
                        TextView z2 = MainExcerciseActivity.this.timerTop;
                        StringBuilder sb62 = new StringBuilder();
                        sb62.append(this.b);
                        sb62.append(str);
                        z2.setText(sb62.toString());
                        MainExcerciseActivity.this.z = ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", " ");
                        MainExcerciseActivity.this.z = MainExcerciseActivity.this.z.toUpperCase();
                        MainExcerciseActivity.this.excName.setText(MainExcerciseActivity.this.z);
                        String str32 = "/";
                        if (MainExcerciseActivity.this.z.equalsIgnoreCase("plank")) {
                            B = MainExcerciseActivity.this.tvProgressMax;
                            StringBuilder sb10 = new StringBuilder();
                            sb10.append(str32);
                            sb10.append(((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles());
                            sb10.append("s");
                            sb2 = sb10.toString();
                        } else {
                            B = MainExcerciseActivity.this.tvProgressMax;
                            StringBuilder sb11 = new StringBuilder();
                            sb11.append(str32);
                            sb11.append(((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles());
                            sb2 = sb11.toString();
                        }
                        B.setText(sb2);
                        MainExcerciseActivity.this.s1 = j;
                        MainExcerciseActivity.this.mainExcCounter = this.b;
                        MainExcerciseActivity.this.mainExcProgress = this.f863a;
                        StringBuilder sb72 = new StringBuilder();
                        sb72.append("onTick: ");
                        sb72.append(this.b);
                        sb72.append("      ");
                        sb72.append(this.c);
                        sb72.append("           ");
                        sb72.append(i3);
                        Log.d("mycount", sb72.toString());
                        i = this.c;
                        i2 = this.b;
                        if (i == i2) {
                            MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                            mainExcerciseActivity2.m.playEarCorn(mainExcerciseActivity2.v);
                            return;
                        }
                        this.c = i2;
                        int i6 = this.c;
                        if (i6 != 1) {
                            if (i6 <= ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) {
                                MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                                if (mainExcerciseActivity3.v == 1) {
                                    absWomenApplication = mainExcerciseActivity3.m;
                                    sb = new StringBuilder();
                                    sb.append(str);
                                    i3 = this.c - 1;
                                    sb.append(i3);
                                    absWomenApplication.speak(sb.toString());
                                    return;
                                }
                            }
                            MainExcerciseActivity mainExcerciseActivity4 = MainExcerciseActivity.this;
                            if (mainExcerciseActivity4.v == 1) {
                                absWomenApplication = mainExcerciseActivity4.m;
                                sb = new StringBuilder();
                                sb.append(str);
                                i3 = ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles();
                                sb.append(i3);
                                absWomenApplication.speak(sb.toString());
                                return;
                            }
                            mainExcerciseActivity4.m.speak(str);
                            return;
                        }
                        return;
                    }
                    n.setText(sb3);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RoundCornerProgressBar x22 = MainExcerciseActivity.this.progressbar;
                float f222 = this.f863a;
                this.f863a = 1.0f + f222;
                x22.setProgress(f222);
                MainExcerciseActivity.this.topProgressBar.setProgress((int) this.f863a);
                TextView z22 = MainExcerciseActivity.this.timerTop;
                StringBuilder sb622 = new StringBuilder();
                sb622.append(this.b);
                sb622.append(str);
                z22.setText(sb622.toString());
                try {
                    MainExcerciseActivity.this.z = ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", " ");
                    MainExcerciseActivity.this.z = MainExcerciseActivity.this.z.toUpperCase();
                    MainExcerciseActivity.this.excName.setText(MainExcerciseActivity.this.z);
                    String str322 = "/";
                    if (MainExcerciseActivity.this.z.equalsIgnoreCase("plank")) {
                    }
                    B.setText(sb2);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                MainExcerciseActivity.this.s1 = j;
                MainExcerciseActivity.this.mainExcCounter = this.b;
                MainExcerciseActivity.this.mainExcProgress = this.f863a;
                StringBuilder sb722 = new StringBuilder();
                sb722.append("onTick: ");
                sb722.append(this.b);
                sb722.append("      ");
                sb722.append(this.c);
                sb722.append("           ");
                sb722.append(i3);
                Log.d("mycount", sb722.toString());
                i = this.c;
                i2 = this.b;
                if (i == i2) {
                }
            }
        }.start();
    }


    public void requestNewInterstitial() {
        this.p.loadAd(this.r);
    }


    public void requestNewInterstitial1() {
        this.q.loadAd(this.s);
    }

    private void setAdmodAds() {
        this.p = new InterstitialAd(this);
        this.p.setAdUnitId(getString(R.string.g_inr));
        this.q = new InterstitialAd(this);
        this.q.setAdUnitId(getString(R.string.g_inr));
        this.r = new AdRequest.Builder().build();
        this.p.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MainExcerciseActivity.this.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        this.r = new AdRequest.Builder().build();
        this.s = new AdRequest.Builder().build();
        this.q.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MainExcerciseActivity.this.requestNewInterstitial1();
            }
        });
        requestNewInterstitial1();
    }

    public /* synthetic */ void a(int i2, ActionEnum actionEnum) {
        this.o = i2;
    }

    @SuppressLint({"SetTextI18n"})
    public void a(long j) {
        String upperCase = this.workoutDataList.get(this.excCouner).getExcName().replace("_", " ").toUpperCase();
        this.nextExerciseName.setText(upperCase);
        this.nextExerciseCycles.setText("x" + this.workoutDataList.get(this.excCouner).getExcCycles());
        this.nextExerciseAnimation.reset();
        for (int addImageFrame : this.workoutDataList.get(this.excCouner).getImageIdList()) {
            this.nextExerciseAnimation.addImageFrame(addImageFrame);
        }
        this.nextExerciseAnimation.startAnimation();
        this.restTimerprogress.setMax((int) (this.REST_TIME_IN_MS / 1000));
        if (j == this.REST_TIME_IN_MS && this.v == 1) {
            this.m.speak("Take rest");
            this.m.speak("the next exercise is " + upperCase);
        }
        this.restTimer = new CountDownTimer(j, 1000) {
            public void onFinish() {
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.D = false;
                mainExcerciseActivity.restScreen.setVisibility(View.GONE);
                MainExcerciseActivity.this.L = false;
                try {
                    long length = (long) (((((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length > 2 ? ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length * ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles() : ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) + 1) * 1000);
                    long unused = MainExcerciseActivity.this.excDurGlobal = length;
                    MainExcerciseActivity.this.pauseMainExcersise.setVisibility(View.VISIBLE);
                    MainExcerciseActivity.this.resumeMainExcersise.setVisibility(View.GONE);
                    Log.i("restscreen", "exc duration" + length + "mainExcCounter" + MainExcerciseActivity.this.mainExcCounter + "mainExcProgress " + MainExcerciseActivity.this.mainExcProgress);
                    if (!MainExcerciseActivity.this.C) {
                        MainExcerciseActivity.this.mainExcTimer(length, MainExcerciseActivity.this.mainExcCounter, MainExcerciseActivity.this.mainExcProgress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.F = false;
                mainExcerciseActivity.D = true;
                Log.i("debashish", NotificationCompat.CATEGORY_MESSAGE + j);
                long j2 = (j - 1000) / 1000;
                MainExcerciseActivity.this.restTimerprogress.setProgress((int) j2);
                TextView p = MainExcerciseActivity.this.countRestTimer;
                p.setText(j2 + "");
                long unused = MainExcerciseActivity.this.s1 = j;
                if (j2 < 4) {
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    if (mainExcerciseActivity2.v == 1) {
                        if (j2 == 3) {
                            mainExcerciseActivity2.m.speak("three ");
                        }
                        if (j2 == 2) {
                            MainExcerciseActivity.this.m.speak("two ");
                        }
                        if (j2 == 1) {
                            MainExcerciseActivity.this.m.speak("one ");
                        }
                        if (j2 == 0 && !MainExcerciseActivity.this.L.booleanValue()) {
                            MainExcerciseActivity.this.m.speak("start");
                            MainExcerciseActivity.this.L = true;
                            return;
                        }
                        return;
                    }
                }
                if (!MainExcerciseActivity.this.m.isSpeaking().booleanValue()) {
                    MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                    mainExcerciseActivity3.m.playEarCorn(mainExcerciseActivity3.v);
                }
            }
        }.start();
    }

    public /* synthetic */ void a(Dialog dialog, View view) {
        this.REST_TIME_IN_MS = (long) ((this.o * 1000) + 2000);
        SharedPreferences.Editor edit = this.y.edit();
        edit.putInt("resttime", this.o);
        edit.apply();
        dialog.dismiss();
    }

    public /* synthetic */ void a(View view) {
        finish();
    }

    public /* synthetic */ void a(View view, int i2) {
//        String appPackage = this.arrPackageData.get(i2).getAppPackage();
//        Library library = this.K;
//        library.actionView("https://play.google.com/store/apps/details?id=" + appPackage);
    }

    public void attachBaseContext(Context context2) {
        super.attachBaseContext(context2);
    }

    public void b() {
        Dialog dialog = new Dialog(this.context, R.style.AppTheme);
        ((Window) Objects.requireNonNull(dialog.getWindow())).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setLayout(-1, -2);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setFlags(1024, 1024);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.activity_customdialog_cycles);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        this.x = (NumberPicker) dialog.findViewById(R.id.countdownNumberPicker);
        this.o = this.y.getInt("resttime", 25);
        this.x.setValue(this.o);
        this.x.setValueChangedListener(new k(this));
        ((Button) dialog.findViewById(R.id.calculate)).setOnClickListener(new i(this, dialog));
        dialog.show();
    }

    public /* synthetic */ void b(Dialog dialog, View view) {
        cancelTimers();
        dialog.dismiss();
        try {
            dialog.dismiss();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(603979776);
            startActivity(intent);
            onSuperBackPressed();
            if (this.q.isLoaded()) {
                this.q.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void b(View view) {
        Log.i("mainexc", "mainExcCounter" + this.mainExcCounter);
        try {
            if (!this.E) {
                this.E = true;
                Log.i("progressvalue", "progressvalueofimageid" + this.workoutDataList.get(this.excCouner).getExcCycles());
                Log.i("progressvalue", "progressvalueofcounter" + this.workoutDataList.get(this.excCouner).getImageIdList().length);
                this.topProgressBar.setProgress((((int) this.excDurGlobal) / 1000) - 1);
                this.excersiseTimer.cancel();
                this.excersiseTimer.onFinish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void c(View view) {
        if (this.m.isSpeaking().booleanValue()) {
            this.m.stop();
        }
        if (this.excCouner > 0) {
            this.topProgressBar.setProgress(0);
            this.excersiseTimer.cancel();
            this.excCouner--;
            this.progressbar.setMax((float) this.workoutDataList.get(this.excCouner).getExcCycles());
            this.tvProgressMax.setText(String.valueOf(this.workoutDataList.get(this.excCouner).getExcCycles()));
            long calculateExTime = calculateExTime(this.excCouner);
            this.excDurGlobal = calculateExTime;
            this.pauseMainExcersise.setVisibility(View.VISIBLE);
            this.resumeMainExcersise.setVisibility(View.GONE);
            double excDayProgress = (double) (this.u.equalsIgnoreCase("beginner") ? this.databaseOperations.getExcDayProgress(this.day) : this.databaseOperations.getExcDayProgressAdv(this.day));
            double size = (double) ((float) this.workoutDataList.size());
            Double.isNaN(size);
            Double.isNaN(excDayProgress);
            this.progress = excDayProgress - (100.0d / size);
            StringBuilder sb = new StringBuilder();
            sb.append("value of progress");
            sb.append(this.databaseOperations.getExcDayProgress(this.day));
            sb.append(" ");
            double size2 = (double) ((float) this.workoutDataList.size());
            Double.isNaN(size2);
            sb.append(100.0d / size2);
            Log.i("dev", sb.toString());
            dataBaseProgressUpdate(this.progress);
            mainExcTimer(calculateExTime, 1, 1.0f);
            return;
        }
        Toast.makeText(this.m, "This is first exercise", Toast.LENGTH_SHORT).show();
    }

    public long calculateExTime(int i2) {
        return this.workoutDataList.get(i2).getImageIdList().length > 2 ? (long) (((this.workoutDataList.get(i2).getImageIdList().length * this.workoutDataList.get(i2).getExcCycles()) + 1) * 1000) : (long) ((this.workoutDataList.get(i2).getExcCycles() + 1) * 1000);
    }

    public void cancelTimers() {
        try {
            if (this.readyToGoTimer != null) {
                this.readyToGoTimer.cancel();
            }
            if (this.excersiseTimer != null) {
                this.excersiseTimer.cancel();
                this.C = false;
            }
            if (this.restTimer != null) {
                this.restTimer.cancel();
                this.D = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void d(View view) {
        CountDownTimer countDownTimer = this.excersiseTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.C = false;
        }
        this.resumeMainExcersise.setVisibility(View.VISIBLE);
        this.pauseMainExcersise.setVisibility(View.GONE);
        b();
    }

    public void dataBaseProgressUpdate(double d) {
        if (this.u.equalsIgnoreCase("beginner")) {
            this.databaseOperations.insertExcDayData(this.day, (float) d);
            this.databaseOperations.insertExcCounter(this.day, this.excCouner);
        } else {
            this.databaseOperations.insertExcDayDataAdv(this.day, (float) d);
            this.databaseOperations.insertExcCounterAdv(this.day, this.excCouner);
        }
        Log.d("CounterValue", this.excCouner + "");
        try {
            Intent intent = new Intent(AppUtils.WORKOUT_BROADCAST_FILTER);
            intent.putExtra(AppUtils.KEY_PROGRESS, d);
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void e(View view) {
        if (this.m.isSpeaking().booleanValue()) {
            this.m.stop();
        }
        if (!this.F) {
            this.F = true;
            this.restTimer.cancel();
            this.restTimer.onFinish();
            this.pauseRestTime.setVisibility(View.VISIBLE);
            this.resumRestTime.setVisibility(View.GONE);
        }
    }

    public /* synthetic */ void f(View view) {
        this.readyToGoTimer.cancel();
        this.readyToGoTimer.onFinish();
    }

    public /* synthetic */ void g(View view) {
        if (this.i % 2 == 0) {
            this.playPause.setBackgroundResource(R.drawable.play);
            this.B = true;
            this.readyToGoTimer.cancel();
        } else {
            this.B = false;
            this.playPause.setBackgroundResource(R.drawable.pause);
            readyToGoFun(this.s1);
        }
        this.i++;
    }

    public /* synthetic */ void h(View view) {
        this.pauseRestTime.setVisibility(View.GONE);
        this.resumRestTime.setVisibility(View.VISIBLE);
        this.restTimer.cancel();
        this.D = false;
    }

    public /* synthetic */ void i(View view) {
        this.pauseRestTime.setVisibility(View.VISIBLE);
        this.resumRestTime.setVisibility(View.GONE);
        a(this.s1);
    }

    public /* synthetic */ void j(View view) {
        CountDownTimer countDownTimer = this.excersiseTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.C = false;
        }
        this.resumeMainExcersise.setVisibility(View.VISIBLE);
        this.pauseMainExcersise.setVisibility(View.GONE);
        excinfo();
    }

    public /* synthetic */ void k(View view) {
        Log.i("pauseMainExcersise", "mainExcCounter" + this.mainExcCounter + "mainExcProgress " + this.mainExcProgress);
        this.pauseMainExcersise.setVisibility(View.GONE);
        this.resumeMainExcersise.setVisibility(View.VISIBLE);
        this.excersiseTimer.cancel();
        this.animImageFull.stopAnimation();
        this.C = false;
    }

    public /* synthetic */ void l(View view) {
        Log.i("resumeMainExcersise", "exc duration" + (this.s1 - 1000) + "mainExcCounter" + this.mainExcCounter + "mainExcProgress " + this.mainExcProgress);
        this.pauseMainExcersise.setVisibility(View.VISIBLE);
        this.resumeMainExcersise.setVisibility(View.GONE);
        mainExcTimer(this.s1 - 1000, this.mainExcCounter, this.mainExcProgress);
    }

    public void onBackPressed() {
        boolean refreshAd_dialog = this.t.refreshAd_dialog();
        Log.d("MainCheck", "" + refreshAd_dialog);
        exitConfirmDialog(refreshAd_dialog);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.K = new Library(this.context);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.w = defaultSharedPreferences.getString("languageToLoad", "");
        this.K.updateLocale(this.w);
        this.y = getSharedPreferences(getResources().getString(R.string.timer_fref_file_name), 0);
        this.v = this.y.getInt("sound", 1);
        Log.i("Debasish", "size of text" + this.w);
        getWindow().addFlags(128);
        setContentView((int) R.layout.mainexcercise_layout);
        this.databaseOperations = new DatabaseOperations(this);
        this.I = (Toolbar) findViewById(R.id.toolbarrest);
        ((Toolbar) findViewById(R.id.mtoolbar)).setNavigationOnClickListener(new r(this));
        this.readytogo_layout = (CoordinatorLayout) findViewById(R.id.readytogo_layout);
        this.o = this.y.getInt("resttime", 25);
        this.REST_TIME_IN_MS = (long) ((this.o * 1000) + 2000);
        this.n = this.y.getInt("readytimer", 15);
        long j = (long) ((this.n * 1000) + 2000);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.workoutDataList = (ArrayList) extras.getSerializable("workoutDataList");
        }
        setAdmodAds();
        this.packageName = getApplicationContext().getPackageName();
        this.day = ((Bundle) Objects.requireNonNull(getIntent().getExtras())).getString("day");
        this.m = AbsWomenApplication.getInstance();
        this.u = defaultSharedPreferences.getString("yoga_type", "beginner");
        String str = this.u;
        if (str != null) {
            this.excCouner = str.equalsIgnoreCase("beginner") ? this.databaseOperations.getDayExcCounter(this.day) : this.databaseOperations.getDayExcCounterAdv(this.day);
        }
        this.t = new AdmobAds(this, getString(R.string.g_native));
        this.progressbar = (RoundCornerProgressBar) findViewById(R.id.progress_one);
        this.animImageFull = (FAImageView) findViewById(R.id.animImageFull);
        this.tvProgress = (TextView) findViewById(R.id.tv_progress);
        this.tvProgressMax = (TextView) findViewById(R.id.tv_progress_max);
        this.timerTop = (TextView) findViewById(R.id.timerTop);
        this.restScreen = (CoordinatorLayout) findViewById(R.id.restScreen);
        this.excName = (TextView) findViewById(R.id.excName);
        this.pauseMainExcersise = (ImageView) findViewById(R.id.pauseMainExcersise);
        this.resumeMainExcersise = (ImageView) findViewById(R.id.resumeMainExcersise);
        ImageView imageView = (ImageView) findViewById(R.id.skip_exc);
        ImageView imageView2 = (ImageView) findViewById(R.id.previous_exc);
        ImageView imageView3 = (ImageView) findViewById(R.id.countdown);
        ImageView imageView4 = (ImageView) findViewById(R.id.help);
        TextView textView = (TextView) findViewById(R.id.skip);
        TextView textView2 = (TextView) findViewById(R.id.skipRestTime);
        if (this.w.equalsIgnoreCase("ru") || this.w.equalsIgnoreCase("de") || this.w.equalsIgnoreCase("nl")) {
            textView2.setTextSize(0, getResources().getDimension(R.dimen.text_medium));
        }
        this.timerprogress = (ProgressBar) findViewById(R.id.timer);
        this.count = (TextView) findViewById(R.id.counting);
        this.playPause = (ImageView) findViewById(R.id.floatingPlay);
        this.excDescInReadyToGo = (TextView) findViewById(R.id.excDescInReadyToGo);
        this.excNameInReadyToGo = (TextView) findViewById(R.id.excNameInReadyToGo);
        this.pauseRestTime = (ImageView) findViewById(R.id.pauseRestTime);
        this.resumRestTime = (ImageView) findViewById(R.id.resumeRestTime);
        this.restTimerprogress = (ProgressBar) findViewById(R.id.rest_timer);
        this.countRestTimer = (TextView) findViewById(R.id.rest_counting);
        this.nextExerciseName = (TextView) findViewById(R.id.nextExerciseName);
        this.nextExerciseCycles = (TextView) findViewById(R.id.nextExerciseCycles);
        this.nextExerciseAnimation = (FAImageView) findViewById(R.id.nextExercisAnimation);
        this.layoutprogress = (LinearLayout) findViewById(R.id.hLayoutprogress);

        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        Gson gson = new Gson();
        String string = sharedPreferences.getString("json_string", "");


//        if (this.arrPackageData == null) {
//            findViewById(R.id.toolbarrest).setVisibility(View.GONE);
//            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) findViewById(R.id.collapsingLayout).getLayoutParams();
//            layoutParams.setScrollFlags(0);
//            findViewById(R.id.collapsingLayout).setLayoutParams(layoutParams);
//        }

        this.progressbar.setMax(10.0f);
        this.animImageFull.setInterval(1000);
        this.animImageFull.setLoop(true);
        this.animImageFull.reset();
        try {
            for (int addImageFrame : this.workoutDataList.get(this.excCouner).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame);
            }
        } catch (IndexOutOfBoundsException unused) {
            this.excCouner = this.workoutDataList.size() - 1;
            for (int addImageFrame2 : this.workoutDataList.get(this.excCouner).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame2);
            }
        }
        this.animImageFull.startAnimation();
        textView2.setOnClickListener(new l(this));
        textView.setOnClickListener(new q(this));
        this.playPause.setOnClickListener(new h(this));
        this.pauseRestTime.setOnClickListener(new t(this));
        this.resumRestTime.setOnClickListener(new n(this));
        imageView4.setOnClickListener(new d(this));
        this.pauseMainExcersise.setOnClickListener(new p(this));
        this.resumeMainExcersise.setOnClickListener(new s(this));
        imageView.setOnClickListener(new g(this));
        imageView2.setOnClickListener(new f(this));
        imageView3.setOnClickListener(new j(this));
        readyToGoFun(j);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        for (int i2 = 0; i2 < this.workoutDataList.size(); i2++) {
            this.topProgressBar = new ProgressBar(this, (AttributeSet) null, 16842872);
            layoutParams2.rightMargin = 2;
            layoutParams2.leftMargin = 2;
            this.topProgressBar.setPadding(0, 0, 0, -8);
            this.topProgressBar.setLayoutParams(layoutParams2);
            this.topProgressBar.setId(i2);
            this.topProgressBar.setScaleY(2.5f);
            this.topProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
            this.layoutprogress.addView(this.topProgressBar);
            this.topProgressBar.setMax(0);
        }
        for (int i3 = 0; i3 < this.excCouner; i3++) {
            this.topProgressBar = (ProgressBar) this.layoutprogress.findViewById(i3);
            this.topProgressBar.setMax(this.workoutDataList.get(this.excCouner).getImageIdList().length * this.workoutDataList.get(this.excCouner).getExcCycles());
            this.topProgressBar.setProgress(this.workoutDataList.get(this.excCouner).getImageIdList().length * this.workoutDataList.get(this.excCouner).getExcCycles());
        }
        getScreenHeightWidth();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    public void onPause() {
        super.onPause();
        cancelTimers();
        if (!this.B) {
            this.i--;
        }
        this.playPause.setBackgroundResource(R.drawable.play);
        this.resumeMainExcersise.setVisibility(View.VISIBLE);
        this.pauseMainExcersise.setVisibility(View.GONE);
        this.resumRestTime.setVisibility(View.VISIBLE);
        this.pauseRestTime.setVisibility(View.GONE);
        this.animImageFull.stopAnimation();
    }

    public void onResume() {
        super.onResume();
        this.pauseMainExcersise.setVisibility(View.GONE);
        this.resumeMainExcersise.setVisibility(View.VISIBLE);
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }

    public void readyToGoFun(long j) {
        this.excDescInReadyToGo.setText(this.workoutDataList.get(this.excCouner).getExcDescResId());
        String upperCase = this.workoutDataList.get(this.excCouner).getExcName().replace("_", " ").toUpperCase();
        this.excNameInReadyToGo.setText(upperCase);
        String lowerCase = upperCase.toLowerCase();
        if (j == 10000 && this.v == 1) {
            this.m.speak("ready to go ");
            AbsWomenApplication absWomenApplication = this.m;
            absWomenApplication.speak("the exercise is " + lowerCase);
        } else {
            this.m.speak("");
            this.m.speak("");
        }
        this.timerprogress.setMax(this.n);
        this.readyToGoTimer = new CountDownTimer(j, 1000) {
            public void onFinish() {
                Log.i("readyToGoTimer", "onFinish: ");
                MainExcerciseActivity.this.M = false;
                MainExcerciseActivity.this.timerprogress.setProgress(0);
                MainExcerciseActivity.this.readytogo_layout.setVisibility(View.GONE);
                long length = (long) (((((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length > 2 ? ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length * ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles() : ((WorkoutData) MainExcerciseActivity.this.workoutDataList.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) + 1) * 1000);
                long unused = MainExcerciseActivity.this.excDurGlobal = length;
                MainExcerciseActivity.this.pauseMainExcersise.setVisibility(View.VISIBLE);
                MainExcerciseActivity.this.resumeMainExcersise.setVisibility(View.GONE);
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.mainExcTimer(length, mainExcerciseActivity.mainExcCounter, MainExcerciseActivity.this.mainExcProgress);
            }

            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                Log.i("readyToGoTimer", "progressbar: " + (((int) j) / 1000));
                long j2 = j - 1000;
                MainExcerciseActivity.this.timerprogress.setProgress(((int) j2) / 1000);
                TextView t = MainExcerciseActivity.this.count;
                StringBuilder sb = new StringBuilder();
                long j3 = j2 / 1000;
                sb.append(j3);
                sb.append("");
                t.setText(sb.toString());
                long unused = MainExcerciseActivity.this.s1 = j;
                if (j3 < 4) {
                    MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                    if (mainExcerciseActivity.v == 1) {
                        if (j3 == 3) {
                            mainExcerciseActivity.m.speak("three ");
                        }
                        if (j3 == 2) {
                            MainExcerciseActivity.this.m.speak("two ");
                        }
                        if (j3 == 1) {
                            MainExcerciseActivity.this.m.speak("one ");
                        }
                        if (j3 == 0 && !MainExcerciseActivity.this.M.booleanValue()) {
                            Log.d("TAG", "onTick: " + j);
                            MainExcerciseActivity.this.m.speak("let's start ");
                            MainExcerciseActivity.this.M = true;
                            return;
                        }
                        return;
                    }
                }
                if (!MainExcerciseActivity.this.m.isSpeaking().booleanValue()) {
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    mainExcerciseActivity2.m.playEarCorn(mainExcerciseActivity2.v);
                }
            }
        }.start();
    }
}
