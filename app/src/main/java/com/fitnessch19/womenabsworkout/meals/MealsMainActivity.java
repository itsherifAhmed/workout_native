package com.fitnessch19.womenabsworkout.meals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.fragments.MealPlanFragment;

public class MealsMainActivity extends AppCompatActivity {
    public AdRequest adRequest;
    public InterstitialAd interstitial;


    public void requestNewInterstitial() {
        this.interstitial.loadAd(this.adRequest);
    }

    private void setAdmodAds() {
        this.interstitial = new InterstitialAd(this);
        this.interstitial.setAdUnitId("");
        this.adRequest = new AdRequest.Builder().build();
        this.interstitial.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MealsMainActivity.this.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            super.onBackPressed();
        } else if (backStackEntryCount != 1) {
            getFragmentManager().popBackStack();
        } else if (this.interstitial.isLoaded()) {
            this.interstitial.show();
        }
        super.onBackPressed();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.meals_activity_main);
        setAdmodAds();
        if (bundle == null) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.replace(R.id.meals_content_fragment, new MealPlanFragment());
            beginTransaction.commit();
        }
    }
}
