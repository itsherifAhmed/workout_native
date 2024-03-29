package com.fitnessch19.womenabsworkout.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.fcm.AppInstalledReciever;
import com.fitnessch19.womenabsworkout.newscreen.Library;
import com.fitnessch19.womenabsworkout.receiver.NotificationReceiver;
import com.fitnessch19.womenabsworkout.utils.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Calendar;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;


public class Start_Activity extends AppCompatActivity {

    public String TAG = "com.outthinking.abc";
    public FrameLayout adLoadingLayout;
    public AdmobAds admobAdsObject = null;

    public Context context;
    public int height;
    public String k = null;
    public String l = null;
    public LayoutManager layoutManager;
    public AppInstalledReciever m;
    public FirebaseAnalytics mFirebaseAnalytics;
    public InterstitialAd mInterstitialAdloading;
    public RecyclerView n;
    public LinearLayoutManager o;

    public SharedPreferences q;
    public TextView r;
    public RecyclerView recycler_view_crosspromtionl;
    public CollapsingToolbarLayout s;
    public View t;
    public AppBarLayout u;
    public RelativeLayout v;
    public TextView w;
    public int width;
    public Library x;


    @NonNull
    private OnClickListener getClickListener() {
        return new OnClickListener() {
            public void onClick(View view) {
                if (view == Start_Activity.this.r) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Start_Activity.this.startActivity(new Intent(Start_Activity.this, After_Main_Activity.class));
                            finish();
                        }
                    }, 100);
                }
            }
        };
    }


//    private void setUpLoadingTimeAd() {
//        this.adLoadingLayout = (FrameLayout) findViewById(R.id.ad_loading_layout);
//        this.adLoadingLayout.setClickable(false);
//        this.adLoadingLayout.setEnabled(false);
//        this.adLoadingLayout.setVisibility(View.VISIBLE);
//        LayoutParams layoutParams = this.adLoadingLayout.getLayoutParams();
//        layoutParams.height = this.height;
//        this.adLoadingLayout.setLayoutParams(layoutParams);
//        this.mInterstitialAdloading = new InterstitialAd(this);
//        this.mInterstitialAdloading.setAdUnitId(getString(R.string.g_inr));
//        this.mInterstitialAdloading.setAdListener(new AdListener() {
//            public void onAdClosed() {
//                Start_Activity.this.adLoadingLayout.setVisibility(View.GONE);
//                Start_Activity.this.b();
//            }
//
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                Start_Activity.this.adLoadingLayout.setVisibility(View.GONE);
//                Start_Activity.this.b();
//            }
//
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                try {
//                    if (Start_Activity.this.mInterstitialAdloading != null && Start_Activity.this.mInterstitialAdloading.isLoaded()) {
//                        Start_Activity.this.mInterstitialAdloading.show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        this.mInterstitialAdloading.loadAd(new Builder().build());
//    }


    public void b() {
        Intent intent = getIntent();
        if (intent != null) {
            this.k = intent.getStringExtra(Constants.APP_PACKAGE_NAME);
            this.l = intent.getStringExtra(Constants.APP_BANNER_URL);
            if (!(this.k == null || this.l == null)) {
                c();
                Editor edit = getApplicationContext().getSharedPreferences(Constants.FCM_CROSS_PROMO_PREF, 0).edit();
                edit.putString("appPackageNameFromFCM", this.k);
                edit.apply();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("appPackageUrlFromFCM: ");
        sb.append(this.k);
        Log.d("onCreate", sb.toString());
    }

    public void c() {
        final Dialog dialog = new Dialog(this, 16974122);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.FCMDialogAnimation;
        dialog.setContentView(R.layout.launch_fcm_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        ((ImageView) dialog.findViewById(R.id.ad_close)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.layoutContainer_dialog);
        frameLayout.setVisibility(0);
        ImageView imageView = (ImageView) frameLayout.findViewById(R.id.image);
        LayoutParams layoutParams = imageView.getLayoutParams();
        int i = this.width;
        layoutParams.width = i - (i / 10);
        imageView.getLayoutParams().height = this.width;
        try {
            if (this.l != null) {
                Picasso.get().load(this.l).resize(this.width - (this.width / 10), this.width).placeholder((int) R.drawable.progress_animation).error((int) R.drawable.error).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
            }
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Start_Activity crossPromoMainActivity = Start_Activity.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("market://details?id=");
                    sb.append(Start_Activity.this.k);
                    crossPromoMainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                    dialog.dismiss();
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString(Param.ITEM_ID, Start_Activity.this.k);
                        bundle.putString(Param.ITEM_NAME, "clicked");
                        bundle.putString(Param.CONTENT_TYPE, "image");
                        Start_Activity.this.mFirebaseAnalytics.logEvent(Event.SELECT_CONTENT, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.show();
    }

//    public void onBackPressed() {
//        if (this.adLoadingLayout.getVisibility() == View.VISIBLE) {
//            this.adLoadingLayout.setVisibility(View.GONE);
//        } else if (this.adLoadingLayout.getVisibility() == View.GONE) {
//            if (this.x.isConnectedToInternet()) {
//
//            }
//            Constants.DayFixed.clear();
//            super.onBackPressed();
//        }
//    }

    @RequiresApi(api = 21)
    @TargetApi(23)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow();
        if (!isTaskRoot()) {
            finish();
            return;
        }
        this.context = this;
        this.x = new Library(this.context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
        if (!this.x.isConnectedToInternet()) {
//            setContentView((int) R.layout.main_cross_promo);
        } else {
            setContentView((int) R.layout.start_activity);
//            this.v = (RelativeLayout) ((CrossPromoMainActivity) this.context).findViewById(R.id.trMorerelative);
//            findViewById(R.id.collapsingLayout).setBackgroundResource(R.drawable.half_image);
//            this.recycler_view_crosspromtionl = (RecyclerView) findViewById(R.id.recycler_view_crosspromtion);
//            this.layoutManager = new GridLayoutManager(this, 3);
//            loadCrossPromotion();
        }
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        this.s = (CollapsingToolbarLayout) ((Start_Activity) this.context).findViewById(R.id.collapsingLayout);
        this.t = ((Start_Activity) this.context).findViewById(R.id.toolbar);
        this.u = (AppBarLayout) ((Start_Activity) this.context).findViewById(R.id.appBarLayout);
//        this.w = (TextView) findViewById(R.id.txt_try_app);
//        setUpLoadingTimeAd();
        this.q = PreferenceManager.getDefaultSharedPreferences(this);
        this.r = (TextView) findViewById(R.id.start);
        PushDownAnim.setOnTouchPushDownAnim((View) this.r).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());
        this.m = new AppInstalledReciever();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_INSTALL");
        intentFilter.addDataScheme("package");
        registerReceiver(this.m, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(this.m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingSuperCall")
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String str = "type";
            if (extras.containsKey(str) && extras.getString(str).equals("test type")) {
                Toast.makeText(this, extras.getString("message"), Toast.LENGTH_SHORT).show();
            }
            this.k = intent.getStringExtra(Constants.APP_PACKAGE_NAME);
            this.l = intent.getStringExtra(Constants.APP_BANNER_URL);
            StringBuilder sb = new StringBuilder();
            sb.append("appPackageNameFromFCM: ");
            sb.append(this.k);
            Log.d("onNewIntent", sb.toString());
            if (this.k != null && this.l != null) {
                Editor edit = getApplicationContext().getSharedPreferences(Constants.FCM_CROSS_PROMO_PREF, 0).edit();
                edit.putString("appPackageNameFromFCM", this.k);
                edit.apply();
                c();
            }
        }
    }

    public void onResume() {
        Log.d("TAG", "onResume");
        PushDownAnim.setOnTouchPushDownAnim((View) this.r).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());
        super.onResume();
    }

    public void onStop() {
        super.onStop();
        this.mInterstitialAdloading = null;
//        this.adLoadingLayout.setVisibility(8);
    }

    public void setAlarm() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 7);
        instance.set(12, 30);
        instance.set(13, 0);
        ((AlarmManager) getSystemService(CATEGORY_ALARM)).setRepeating(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(getApplicationContext(), 100, new Intent(getApplicationContext(), NotificationReceiver.class), 134217728));
    }
}
