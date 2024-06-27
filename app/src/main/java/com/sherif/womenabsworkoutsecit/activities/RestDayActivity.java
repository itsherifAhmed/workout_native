package com.sherif.womenabsworkoutsecit.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.a.a.u;

public class RestDayActivity extends AppCompatActivity {
    public /* synthetic */ void a(View view) {
        finish();
    }

    public void clicked(View view) {
        finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.rest_day_layout);


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exc_details_layout_mtoolbar);
        ((TextView) toolbar.findViewById(R.id.exc_details_layout_toolbar_title)).setText(R.string.restday);
        toolbar.setNavigationOnClickListener(new u(this));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }
}
