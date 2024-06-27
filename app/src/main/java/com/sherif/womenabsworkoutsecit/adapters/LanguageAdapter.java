package com.sherif.womenabsworkoutsecit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sherif.womenabsworkoutsecit.R;

import java.util.Locale;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    /* renamed from: a  reason: collision with root package name */
    public SharedPreferences f876a;
    public String[] arr;
    public int[] arr_image;
    public SharedPreferences b;
    public SharedPreferences c;
    public Context context;
    public String d;
    public String[] e = {"en", "zh", "ru", "fr", "es", "ar", "ja", "de", "ko", "pt", "it", "in", "nl"};
    public SharedPreferences.Editor prefsEditor;

    class ViewHolder extends RecyclerView.ViewHolder {

        /* renamed from: a  reason: collision with root package name */
        public TextView f878a;
        public RadioButton b;
        public RelativeLayout c;

        public ViewHolder(@NonNull View view) {
            super(view);
            LanguageAdapter.this.f876a = LanguageAdapter.this.context.getSharedPreferences("radio_button", 0);
            this.f878a = (TextView) view.findViewById(R.id.language_name);
            this.b = (RadioButton) view.findViewById(R.id.radio_button);
            this.c = (RelativeLayout) view.findViewById(R.id.flag_layout);
        }
    }

    public LanguageAdapter(int[] iArr, String[] strArr, Context context2) {
        this.arr = strArr;
        this.context = context2;
        this.arr_image = iArr;
    }


    public void updateLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = this.context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public int getItemCount() {
        return this.arr.length;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.f878a.setText(this.arr[i]);
        boolean z = false;
        this.c = this.context.getSharedPreferences("radio_button", 0);
        final SharedPreferences.Editor edit = this.c.edit();
        RadioButton radioButton = viewHolder.b;
        if (i == this.f876a.getInt("position", 0)) {
            z = true;
        }
        radioButton.setChecked(z);
        viewHolder.c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LanguageAdapter.this.notifyDataSetChanged();
                ((Activity) LanguageAdapter.this.context).finish();
                LanguageAdapter languageAdapter = LanguageAdapter.this;
                String[] strArr = languageAdapter.e;
//                int i = i;
                languageAdapter.d = strArr[i];
                edit.putInt("position", i);
                edit.apply();
                LanguageAdapter languageAdapter2 = LanguageAdapter.this;
                languageAdapter2.b = PreferenceManager.getDefaultSharedPreferences(languageAdapter2.context.getApplicationContext());
                LanguageAdapter languageAdapter3 = LanguageAdapter.this;
                SharedPreferences.Editor unused = languageAdapter3.prefsEditor = languageAdapter3.b.edit();
                LanguageAdapter.this.prefsEditor.putString("languageToLoad", LanguageAdapter.this.d);
                LanguageAdapter.this.prefsEditor.apply();
                LanguageAdapter languageAdapter4 = LanguageAdapter.this;
                languageAdapter4.updateLocale(languageAdapter4.d);
                LanguageAdapter.this.context.startActivity(((Activity) LanguageAdapter.this.context).getIntent());
            }
        });
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.language_row, viewGroup, false));
    }
}
