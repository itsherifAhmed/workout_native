package com.fitnessch19.womenabsworkout.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.fitnessch19.womenabsworkout.utils.Constants;

public class AppInstalledReciever extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
        String string = context.getSharedPreferences(Constants.FCM_CROSS_PROMO_PREF, 0).getString("appPackageNameFromFCM", "");
        Log.i("onReceive ", "packageName outside if: " + encodedSchemeSpecificPart);
        try {
            if (encodedSchemeSpecificPart.equalsIgnoreCase(string)) {
                Log.i("onReceive ", "packageName inside if: " + encodedSchemeSpecificPart);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, encodedSchemeSpecificPart);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "installed-" + encodedSchemeSpecificPart);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
            Log.i("onReceive ", "packageName inside try: " + encodedSchemeSpecificPart);
            context.unregisterReceiver(this);
        } catch (Exception e) {
            Log.i("onReceive ", "packageName inside catch: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
