package com.sherif.womenabsworkoutsecit.utils;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.sherif.womenabsworkoutsecit.R;

import java.util.HashMap;
import java.util.Locale;

public class AbsWomenApplication extends Application {
    public static AbsWomenApplication absWomenApplication;
    public TextToSpeech textToSpeech;

    public static AbsWomenApplication getInstance() {
        return absWomenApplication;
    }

    public void addEarCorn(int i) {
        try {
            if (this.textToSpeech == null) {
                return;
            }
            if (i == 1) {
                this.textToSpeech.addEarcon("tick", getApplicationContext().getPackageName(), R.raw.clocktick_trim);
            } else {
                this.textToSpeech.addEarcon("", getApplicationContext().getPackageName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isSpeaking() {
        return Boolean.valueOf(this.textToSpeech.isSpeaking());
    }

    public void onCreate() {
        super.onCreate();
        absWomenApplication = this;
        new Thread(new Runnable() {
            public void run() {
                AbsWomenApplication absWomenApplication = AbsWomenApplication.this;
                if (absWomenApplication.textToSpeech == null) {
                    absWomenApplication.textToSpeech = new TextToSpeech(AbsWomenApplication.getInstance().getApplicationContext(), new TextToSpeech.OnInitListener() {
                        public void onInit(int i) {
                            if (i == 0) {
                                try {
                                    if (AbsWomenApplication.this.textToSpeech != null) {
                                        AbsWomenApplication.this.textToSpeech.setLanguage(Locale.US);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void playEarCorn(int i) {
        try {
            if (this.textToSpeech == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (i == 1) {
                    this.textToSpeech.playEarcon("tick", 0, (Bundle) null, getApplicationContext().getPackageName());
                } else {
                    this.textToSpeech.playEarcon("", 0, (Bundle) null, getApplicationContext().getPackageName());
                }
            } else if (i == 0) {
                this.textToSpeech.playEarcon("tick", 0, (HashMap) null);
            } else {
                this.textToSpeech.playEarcon("", 0, (HashMap) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speak(String str) {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.setSpeechRate(1.0f);
                this.textToSpeech.speak(str, 1, (HashMap) null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
