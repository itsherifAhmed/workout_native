package com.sherif.womenabsworkoutsecit.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.NativeAd;

import com.sherif.womenabsworkoutsecit.R;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.Locale;
import kr.pe.burt.android.lib.faimageview.FAImageView;

public class ExcDetailsActivity extends AppCompatActivity {
    private AdChoicesView adChoicesView;
    private LinearLayout adView;
    private boolean adloaded = false;
    private AdmobAds admobAdsObject = null;
    private FAImageView animImageFull;
    int i;
    TextView l;
    Context m;
    private SharedPreferences mSharedPreferences;
    LayoutInflater n;
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    int o;
    int p;
    int q = 0;
    String r;

    public static int dpToPx() {
        return (int) (Resources.getSystem().getDisplayMetrics().density * 50.0f);
    }

    private void getScreenHeightWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.o = displayMetrics.heightPixels;
        this.p = displayMetrics.widthPixels;
    }

    private boolean isConnectedToInternet() {
        NetworkInfo[] allNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
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

    private void updateLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.nativeAdContainer = (LinearLayout) findViewById(R.id.nativeAdContainer);
        this.n = (LayoutInflater) getSystemService("layout_inflater");
        if (isConnectedToInternet()) {
            this.admobAdsObject = new AdmobAds(this.m, this.nativeAdContainer, getString(R.string.g_native));
            this.admobAdsObject.refreshAd();
        }
    }
    public int editCycle;
    public boolean editedValue = false;



    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.r = this.mSharedPreferences.getString("languageToLoad", "");
        updateLocale(this.r);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.exc_details_layout);
        this.m = this;

        Bundle extras = getIntent().getExtras();
        int[] intArray = extras.getIntArray("framesIdArray");
        String string = extras.getString("excName");
        extras.getInt("excCycle");
        int i2 = extras.getInt("excCycle");
        this.i = extras.getInt("excNameDescResId");
        String upperCase = string.replace("_", " ").toUpperCase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.exc_details_layout_mtoolbar);
        ((TextView) toolbar.findViewById(R.id.exc_details_layout_toolbar_title)).setText(upperCase);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExcDetailsActivity.this.finish();
            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setMax(100);
        numberPicker.setMin(5);
        numberPicker.setValue(i2);
        this.editCycle = i2;
        numberPicker.setValueChangedListener(new ValueChangedListener() {
            public void valueChanged(int i, ActionEnum actionEnum) {
                int unused = ExcDetailsActivity.this.editCycle = i;
                Log.e("TAG", "Np val " + i);
                boolean unused2 = ExcDetailsActivity.this.editedValue = true;
            }
        });
        this.l = (TextView) findViewById(R.id.description_exDetail);
        this.animImageFull = (FAImageView) findViewById(R.id.animation_exDetail);
        this.animImageFull.setInterval(1000);
        this.animImageFull.setLoop(true);
        this.animImageFull.reset();
        for (int addImageFrame : intArray) {
            this.animImageFull.addImageFrame(addImageFrame);
        }
        this.animImageFull.startAnimation();
        this.l.setText(this.i);
        getScreenHeightWidth();
        b();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }
    public void onBackPressed() {
        super.onBackPressed();


//        if (this.editedValue) {
//            Toast.makeText(getApplicationContext(), "Exercise cycles are updated.", Toast.LENGTH_SHORT).show();
//        }
//        this.editedValue = false;
    }
}
