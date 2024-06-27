package com.sherif.womenabsworkoutsecit.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseIIDService";

    private void sendRegistrationToServer(String str) {
    }

    public void onNewToken(String str) {
        super.onNewToken(str);
        Log.d("NEW_TOKEN", str);
    }
}
