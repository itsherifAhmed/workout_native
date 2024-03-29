package com.fitnessch19.womenabsworkout.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.a.b.b;
import com.fitnessch19.womenabsworkout.activities.AdmobAds;
import com.fitnessch19.womenabsworkout.activities.After_Main_Activity;
import com.fitnessch19.womenabsworkout.activities.MainActivity;
import com.fitnessch19.womenabsworkout.adapters.AllDayAdapter;
import com.fitnessch19.womenabsworkout.adapters.WorkoutData;
import com.fitnessch19.womenabsworkout.database.DatabaseHelper;
import com.fitnessch19.womenabsworkout.database.DatabaseOperations;
import com.fitnessch19.womenabsworkout.newscreen.Library;
import com.fitnessch19.womenabsworkout.utils.AppUtils;
import com.fitnessch19.womenabsworkout.utils.Constants;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment implements RewardedVideoAdListener {
    public AdmobAds admobAdsObject = null;
    public String excercise_type;
    public TextView free_weight;
    public Library library;
    public RewardedVideoAd mRewardedVideoAd;
    public LinearLayout nativeAdContainer;
    public ImageView paid_weight, calculate, dietplan;

    public AllDayAdapter adapter;

    public TextView percentScore1;
    public SharedPreferences.Editor prefsEditor;
    public ProgressBar progressBar;
    public TextView dayleft;
    public int daysCompletionConter = 0;
    public SharedPreferences mSharedPreferences;
    public ArrayList<String> arr = new ArrayList<>();
    public double total_progress = 0.0d;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;
    public DatabaseOperations databaseOperations;
    public String exc_type;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtils.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) workoutDataList.get(workoutPosition)).setProgress((float) doubleExtra);
                total_progress = 0.0d;
                int i = 0;
                daysCompletionConter = 0;
                while (true) {
                    String str = "dev";
                    if (i < Constants.TOTAL_DAYS) {
                        double v = total_progress;
                        double progress = (double) ((WorkoutData) workoutDataList.get(i)).getProgress();
                        Double.isNaN(progress);
                        total_progress = (double) ((float) (v + ((progress * 4.348d) / 100.0d)));
                        StringBuilder sb = new StringBuilder();
                        sb.append("totalprogressreceiver");
                        sb.append(total_progress);
                        Log.i(str, sb.toString());
                        if (((WorkoutData) workoutDataList.get(i)).getProgress() >= 99.0f) {
                            daysCompletionConter = daysCompletionConter + 1;
                        }
                        i++;
                    } else {
                        daysCompletionConter = daysCompletionConter + (daysCompletionConter / 3);
                        progressBar.setProgress((int) total_progress);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("totalprogressreceiver1");
                        sb2.append(total_progress);
                        Log.i(str, sb2.toString());
                        TextView c = percentScore1;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append((int) total_progress);
                        sb3.append("%");
                        c.setText(sb3.toString());
                        TextView d = dayleft;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(Constants.TOTAL_DAYS - daysCompletionConter);
                        sb4.append(getString(R.string.dayleft));
                        d.setText(sb4.toString());
                        adapter.notifyDataSetChanged();
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

    After_Main_Activity mainAcdsctivity;

    public WorkoutFragment(After_Main_Activity mainActivity) {
        this.mainAcdsctivity = mainActivity;
    }

    public static WorkoutFragment newInstance(String str, String str2, After_Main_Activity mainActivity) {
        WorkoutFragment mainFragment = new WorkoutFragment(mainActivity);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    private void displayNativeAd() {
        if (this.library.isConnectedToInternet()) {
            this.admobAdsObject = new AdmobAds(getActivity(), this.nativeAdContainer, getString(R.string.g_native));
            this.admobAdsObject.refreshAd();
        }
    }

    @NonNull
    private View.OnClickListener getClickListener() {
        return new b(this);
    }

    private void loadRewardedVideoAd() {
        Log.d("TAG", "loadRewardedVideoAd");
        this.mRewardedVideoAd.loadAd(getString(R.string.g_inr), new AdRequest.Builder().build());
    }

    public void a(View view) {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }




    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_workout, viewGroup, false);

        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.prefsEditor = this.mSharedPreferences.edit();
        String str = "beginner";
        this.exc_type = this.mSharedPreferences.getString("yoga_type", str);
        List<WorkoutData> allDaysProgressAdv;

        progressBar = (ProgressBar) inflate.findViewById(R.id.progress);
        percentScore1 = (TextView) inflate.findViewById(R.id.percentScore);
        dayleft = (TextView) inflate.findViewById(R.id.daysLeft);


        this.databaseOperations = new DatabaseOperations(getContext());
        String str2 = "thirtyday";
        boolean z = this.mSharedPreferences.getBoolean(str2, false);
        String str3 = "daysInserted";
        if (!this.mSharedPreferences.getBoolean(str3, false) && this.databaseOperations.CheckDBEmpty(DatabaseHelper.EXC_DAY_TABLE) == 0) {
            this.databaseOperations.insertExcALLDayData();
            SharedPreferences.Editor edit = this.mSharedPreferences.edit();
            edit.putBoolean(str3, true);
            edit.apply();
        }
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
//        if (this.exc_type.equalsIgnoreCase(str)) {
//            allDaysProgressAdv = this.databaseOperations.getAllDaysProgress();
//        } else {
//            if (!this.databaseOperations.tableExists(DatabaseHelper.EXC_DAY_TABLE_ADVANCED)) {
//                this.databaseOperations.createAdvTable();
//            }
//            if (this.databaseOperations.CheckDBEmpty(DatabaseHelper.EXC_DAY_TABLE_ADVANCED) == 0) {
//                this.databaseOperations.insertExcALLDayDataAdv();
//                SharedPreferences.Editor edit2 = this.mSharedPreferences.edit();
//                edit2.putBoolean(str3, true);
//                edit2.apply();
//            }
//            allDaysProgressAdv = this.databaseOperations.getAllDaysProgressAdv();
//        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();
        ;
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progress);
//        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
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

        if (z) {
            SharedPreferences.Editor edit3 = this.mSharedPreferences.edit();
            edit3.putBoolean(str2, false);
            edit3.apply();
//            restartExcercise();
            this.daysCompletionConter = 0;
        }
        getActivity().registerReceiver(this.progressReceiver, new IntentFilter(AppUtils.WORKOUT_BROADCAST_FILTER));
        if (this.daysCompletionConter > 4) {


        }

        inflate.findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                mainAcdsctivity.loadFragment_Calculator(new Calculate_Fragment());


            }
        });
        inflate.findViewById(R.id.startdiet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAcdsctivity.loadFragment_Mealplan(new MealPlanFragment());
            }
        });


        this.library = new Library(getActivity());
        this.nativeAdContainer = (LinearLayout) inflate.findViewById(R.id.nativeAdContainer);
        displayNativeAd();
//        ((Toolbar) inflate.findViewById(R.id.mtoolbar)).setNavigationOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                WorkoutFragment.this.getActivity().finish();
//            }
//        });
        this.free_weight = (TextView) inflate.findViewById(R.id.startexercise);
        this.calculate = (ImageView) inflate.findViewById(R.id.calculate);
        this.dietplan = (ImageView) inflate.findViewById(R.id.startdiet);

        MobileAds.initialize((Context) getActivity(), String.valueOf(R.string.admob_app_id));
        this.mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        this.mRewardedVideoAd.setRewardedVideoAdListener(this);
        PushDownAnim.setOnTouchPushDownAnim(this.free_weight).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());
        PushDownAnim.setOnTouchPushDownAnim(this.paid_weight).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());

        loadRewardedVideoAd();
        return inflate;
    }

    public void onDestroy() {
        Log.d("TAG", "onDestroy");
        RewardedVideoAd rewardedVideoAd = this.mRewardedVideoAd;
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy(getActivity());
        }
        super.onDestroy();
    }

    public void onPause() {
        RewardedVideoAd rewardedVideoAd = this.mRewardedVideoAd;
        if (rewardedVideoAd != null) {
            rewardedVideoAd.pause(getActivity());
        }
        super.onPause();
    }

    public void onResume() {
        RewardedVideoAd rewardedVideoAd = this.mRewardedVideoAd;
        if (rewardedVideoAd != null) {
            rewardedVideoAd.resume(getActivity());
        }
        PushDownAnim.setOnTouchPushDownAnim(free_weight).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());
        PushDownAnim.setOnTouchPushDownAnim(paid_weight).setScale(1).setDurationPush(50).setDurationRelease(50).setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR).setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR).setOnClickListener(getClickListener());

        super.onResume();
    }

    public void onRewarded(RewardItem rewardItem) {
        Constants.adOfTheDay = true;
    }

    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
        if (!this.excercise_type.equalsIgnoreCase("advanced")) {
            return;
        }
        if (Constants.adOfTheDay) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("yoga_type", this.excercise_type);
            this.library.saveString("yoga_type", this.excercise_type, getActivity());
            startActivity(intent);
            return;
        }
        Toast.makeText(getActivity(), getString(R.string.plzwatchvideo), Toast.LENGTH_SHORT).show();
    }

    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();
    }

    public void onRewardedVideoAdLeftApplication() {
    }

    public void onRewardedVideoAdLoaded() {
    }

    public void onRewardedVideoAdOpened() {
    }

    public void onRewardedVideoCompleted() {
        Constants.adOfTheDay = true;
    }

    public void onRewardedVideoStarted() {
    }


}
