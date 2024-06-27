package com.sherif.womenabsworkoutsecit.a.a;

import com.sherif.womenabsworkoutsecit.activities.MainExcerciseActivity;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

/* compiled from: lambda */
public final /* synthetic */ class k implements ValueChangedListener {

    /* renamed from: a  reason: collision with root package name */
    private final /* synthetic */ MainExcerciseActivity f10a;

    public /* synthetic */ k(MainExcerciseActivity mainExcerciseActivity) {
        this.f10a = mainExcerciseActivity;
    }

    public final void valueChanged(int i, ActionEnum actionEnum) {
        this.f10a.a(i, actionEnum);
    }
}
