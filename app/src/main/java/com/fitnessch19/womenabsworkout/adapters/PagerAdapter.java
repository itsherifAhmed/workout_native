package com.fitnessch19.womenabsworkout.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fitnessch19.womenabsworkout.fragments.TabFragmentWeek;

public class PagerAdapter extends FragmentStatePagerAdapter {

    /* renamed from: a  reason: collision with root package name */
    public int f881a;
    public Bundle b;
    public TabFragmentWeek c;

    public PagerAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.f881a = i;
    }

    public int getCount() {
        return this.f881a;
    }

    public Fragment getItem(int i) {
        Bundle bundle;
        String str;
        if (i == 0) {
            this.c = new TabFragmentWeek();
            this.b = new Bundle();
            bundle = this.b;
            str = "WEEK1";
        } else if (i == 1) {
            this.c = new TabFragmentWeek();
            this.b = new Bundle();
            bundle = this.b;
            str = "WEEK2";
        } else if (i == 2) {
            this.c = new TabFragmentWeek();
            this.b = new Bundle();
            bundle = this.b;
            str = "WEEK3";
        } else if (i == 3) {
            this.c = new TabFragmentWeek();
            this.b = new Bundle();
            bundle = this.b;
            str = "WEEK4";
        } else if (i != 4) {
            return null;
        } else {
            this.c = new TabFragmentWeek();
            this.b = new Bundle();
            bundle = this.b;
            str = "WEEK5";
        }
        bundle.putString("WEEK", str);
        this.c.setArguments(this.b);
        return this.c;
    }
}
