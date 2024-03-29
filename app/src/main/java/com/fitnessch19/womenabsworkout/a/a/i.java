package com.fitnessch19.womenabsworkout.a.a;

import android.app.Dialog;
import android.view.View;

import com.fitnessch19.womenabsworkout.activities.MainExcerciseActivity;

/* compiled from: lambda */
public final /* synthetic */ class i implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    private final /* synthetic */ MainExcerciseActivity f8a;
    private final /* synthetic */ Dialog b;

    public /* synthetic */ i(MainExcerciseActivity mainExcerciseActivity, Dialog dialog) {
        this.f8a = mainExcerciseActivity;
        this.b = dialog;
    }

    public final void onClick(View view) {
        this.f8a.a(this.b, view);
    }
}
