
package com.fitnessch19.womenabsworkout.activities;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.fitnessch19.womenabsworkout.ConstantValues;
import com.fitnessch19.womenabsworkout.R;


public class SplashActivity extends AppCompatActivity {

    boolean status = false;
    public Boolean logg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.logg = Boolean.valueOf(getSharedPreferences(ConstantValues.PREFS_NAME, 0).getBoolean("logged", false));

        if(isWorkingInternetPersent()){
            if (SplashActivity.this.logg.booleanValue()) {
                splash();
                return;
            }
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, UserDetailsActivity.class));
            SplashActivity.this.finish();

        }
        else{
            showAlertDialog(SplashActivity.this, "No Internet Connection!",
                    "Please Connect to internet and\n try Again..", false);
        }
    }
    public void splash() {
        Thread timerTread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), After_Main_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerTread.start();
    }
    public boolean isWorkingInternetPersent() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.mipmap.logo : R.mipmap.logo);

        // Setting OK Button

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                System.exit(0);
            }
        });


        // Showing Alert Message
        alertDialog.show();
    }



}