package com.fitnessch19.womenabsworkout.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.fitnessch19.womenabsworkout.R;
import com.fitnessch19.womenabsworkout.adapters.IndividualDayAdapter;
import com.fitnessch19.womenabsworkout.adapters.WorkoutData;
import com.fitnessch19.womenabsworkout.database.DatabaseOperations;
import com.fitnessch19.womenabsworkout.listners.RecyclerItemClickListener;
import com.fitnessch19.womenabsworkout.newscreen.Library;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DayActivity extends AppCompatActivity {
    Library A;
    private LinearLayout container;
    private SharedPreferences.Editor editorDay;
    RecyclerView i;
    Button l;
    LinearLayoutManager m;
    private GridLayoutManager manager;
    IndividualDayAdapter n;
    String o;
    float p;
    private SharedPreferences preferencesDay;
    HashMap<String, Integer> q;
    HashMap<String, Integer> r;
    int[] s = {R.array.day1, R.array.day2, R.array.day3, R.array.day4, R.array.day5, R.array.day6, R.array.day7, R.array.day8, R.array.day9, R.array.day10, R.array.day11, R.array.day12, R.array.day13, R.array.day14, R.array.day15, R.array.day16, R.array.day17, R.array.day18, R.array.day19, R.array.day20, R.array.day21, R.array.day22, R.array.day23, R.array.day24, R.array.day25, R.array.day26, R.array.day27, R.array.day28, R.array.day29, R.array.day30};
    int[] t = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    int u = -1;
    ArrayList<WorkoutData> v;
    InterstitialAd w;
    AdRequest x;
    Intent y;
    String z;

    public static /* synthetic */ void lambda$onCreate$1(DayActivity dayActivity, View view, int i2) {
        if (i2 < dayActivity.v.size()) {
            Intent intent = new Intent(dayActivity, ExcDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putIntArray("framesIdArray", dayActivity.v.get(i2).getImageIdList());
            bundle.putString("excName", dayActivity.v.get(i2).getExcName());
            bundle.putInt("excNameDescResId", dayActivity.r.get(dayActivity.v.get(i2).getExcName()).intValue());
            bundle.putInt("excCycle", dayActivity.v.get(i2).getExcCycles());
            intent.putExtras(bundle);
            dayActivity.startActivity(intent);
        }
    }

    public static /* synthetic */ void lambda$onCreate$2(DayActivity dayActivity, View view) {
        dayActivity.y = new Intent(dayActivity, MainExcerciseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("workoutDataList", dayActivity.v);
        dayActivity.y.putExtras(bundle);
        dayActivity.y.putExtra("day", dayActivity.o);
        dayActivity.p = new DatabaseOperations(dayActivity).getExcDayProgress(dayActivity.o);
        dayActivity.y.putExtra(NotificationCompat.CATEGORY_PROGRESS, dayActivity.p);
        if (dayActivity.w.isLoaded()) {
            dayActivity.w.show();
        } else {
            dayActivity.startActivity(dayActivity.y);
        }
    }

    /* access modifiers changed from: private */
    public void requestNewInterstitial() {
        this.w.loadAd(this.x);
    }

    private void setAdmodAds() {
        this.w = new InterstitialAd(this);
        this.w.setAdUnitId(getString(R.string.g_inr));
        this.x = new AdRequest.Builder().build();
        this.w.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                DayActivity.this.startActivity(DayActivity.this.y);
                DayActivity.this.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
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
    public ArrayList<WorkoutData> b() {
        ArrayList<WorkoutData> arrayList = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(this.s[this.u]);
        int[] intArray = getResources().getIntArray(this.t[this.u]);
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(this.q.get(stringArray[i2]).intValue());
            int length = obtainTypedArray.length();
            int[] iArr = new int[length];
            WorkoutData workoutData = new WorkoutData();
            for (int i3 = 0; i3 < length; i3++) {
                iArr[i3] = obtainTypedArray.getResourceId(i3, -1);
            }
            workoutData.setExcName(stringArray[i2]);
            workoutData.setExcDescResId(this.r.get(stringArray[i2]).intValue());
            workoutData.setExcCycles(intArray[i2]);
            workoutData.setPosition(i2);
            workoutData.setImageIdList(iArr);
            arrayList.add(workoutData);
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public void c() {
        this.q = new HashMap<>();
        this.q.put(getString(R.string.trunk_rotation), Integer.valueOf(R.array.trunk_rotation));
        this.q.put(getString(R.string.mountain_climber), Integer.valueOf(R.array.mountain_climber));
        this.q.put(getString(R.string.clapping_crunches), Integer.valueOf(R.array.clapping_crunches));
        this.q.put(getString(R.string.swimming_and_superman), Integer.valueOf(R.array.swimming_and_superman));
        this.q.put(getString(R.string.butt_bridge), Integer.valueOf(R.array.butt_bridge));
        this.q.put(getString(R.string.flutter_kicks), Integer.valueOf(R.array.flutter_kicks));
        this.q.put(getString(R.string.plank), Integer.valueOf(R.array.plank));
        this.q.put(getString(R.string.reverse_crunches), Integer.valueOf(R.array.reverse_crunches));
        this.q.put(getString(R.string.bent_leg_twist), Integer.valueOf(R.array.bent_leg_twist));
        this.q.put(getString(R.string.bicycle_crunches), Integer.valueOf(R.array.bicycle_crunches));
        this.q.put(getString(R.string.russian_twist), Integer.valueOf(R.array.russian_twist));
        this.q.put(getString(R.string.reclined_oblique_twist), Integer.valueOf(R.array.reclined_oblique_twist));
        this.q.put(getString(R.string.cross_arm_crunches), Integer.valueOf(R.array.cross_arm_crunches));
        this.q.put(getString(R.string.standing_bicycle), Integer.valueOf(R.array.standing_bicycle));
        this.q.put(getString(R.string.leg_drops), Integer.valueOf(R.array.leg_drops));
        this.q.put(getString(R.string.side_leg_rise_left), Integer.valueOf(R.array.side_leg_rise_left));
        this.q.put(getString(R.string.side_leg_rise_right), Integer.valueOf(R.array.side_leg_rise_right));
        this.q.put(getString(R.string.long_arm_crunches), Integer.valueOf(R.array.long_arm_crunches));
        this.q.put(getString(R.string.dead_bug), Integer.valueOf(R.array.dead_bug));
        this.q.put(getString(R.string.cross_body_mountain_climber), Integer.valueOf(R.array.cross_body_mountain_climber));
        this.q.put(getString(R.string.roll_up), Integer.valueOf(R.array.roll_up));
        this.q.put(getString(R.string.side_plank_hip_lift_left), Integer.valueOf(R.array.Side_plank_hip_lift_left));
        this.q.put(getString(R.string.side_plank_hip_lift_right), Integer.valueOf(R.array.Side_plank_hip_lift_right));
        this.q.put(getString(R.string.v_sits), Integer.valueOf(R.array.V_sits));
        this.q.put(getString(R.string.windshield_wipers), Integer.valueOf(R.array.Windshield_wipers));
        this.q.put(getString(R.string.reverse_crunch), Integer.valueOf(R.array.reverse_crunch));
    }

    /* access modifiers changed from: package-private */
    public void d() {
        this.r = new HashMap<>();
        this.r.put(getString(R.string.trunk_rotation), Integer.valueOf(R.string.trunk_rotation_desc));
        this.r.put(getString(R.string.mountain_climber), Integer.valueOf(R.string.mountain_climber_desc));
        this.r.put(getString(R.string.clapping_crunches), Integer.valueOf(R.string.clapping_crunches_desc));
        this.r.put(getString(R.string.swimming_and_superman), Integer.valueOf(R.string.swimming_and_superman_desc));
        this.r.put(getString(R.string.butt_bridge), Integer.valueOf(R.string.butt_bridge_desc));
        this.r.put(getString(R.string.flutter_kicks), Integer.valueOf(R.string.flutter_kicks_desc));
        this.r.put(getString(R.string.plank), Integer.valueOf(R.string.plank_desc));
        this.r.put(getString(R.string.reverse_crunches), Integer.valueOf(R.string.reverse_crunches_desc));
        this.r.put(getString(R.string.bent_leg_twist), Integer.valueOf(R.string.bent_leg_twist_desc));
        this.r.put(getString(R.string.bicycle_crunches), Integer.valueOf(R.string.bicycle_crunches_desc));
        this.r.put(getString(R.string.russian_twist), Integer.valueOf(R.string.russian_twist_desc));
        this.r.put(getString(R.string.reclined_oblique_twist), Integer.valueOf(R.string.reclined_oblique_twist_desc));
        this.r.put(getString(R.string.cross_arm_crunches), Integer.valueOf(R.string.cross_arm_crunches_desc));
        this.r.put(getString(R.string.standing_bicycle), Integer.valueOf(R.string.standing_bicycle_desc));
        this.r.put(getString(R.string.leg_drops), Integer.valueOf(R.string.leg_drops_desc));
        this.r.put(getString(R.string.side_leg_rise_left), Integer.valueOf(R.string.side_leg_rise_left_desc));
        this.r.put(getString(R.string.side_leg_rise_right), Integer.valueOf(R.string.side_leg_rise_right_desc));
        this.r.put(getString(R.string.long_arm_crunches), Integer.valueOf(R.string.long_arm_crunches_desc));
        this.r.put(getString(R.string.dead_bug), Integer.valueOf(R.string.dead_bug_desc));
        this.r.put(getString(R.string.cross_body_mountain_climber), Integer.valueOf(R.string.cross_body_mountain_climber_desc));
        this.r.put(getString(R.string.roll_up), Integer.valueOf(R.string.roll_up_desc));
        this.r.put(getString(R.string.side_plank_hip_lift_left), Integer.valueOf(R.string.Side_plank_hip_lift_left_desc));
        this.r.put(getString(R.string.side_plank_hip_lift_right), Integer.valueOf(R.string.Side_plank_hip_lift_right_desc));
        this.r.put(getString(R.string.v_sits), Integer.valueOf(R.string.V_sits_desc));
        this.r.put(getString(R.string.windshield_wipers), Integer.valueOf(R.string.Windshield_wipers_desc));
        this.r.put(getString(R.string.reverse_crunch), Integer.valueOf(R.string.reverse_crunches_descip));
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 21)
    @SuppressLint({"WrongConstant"})
    @TargetApi(23)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.preferencesDay = PreferenceManager.getDefaultSharedPreferences(this);
        this.z = this.preferencesDay.getString("languageToLoad", "");
        updateLocale(this.z);
        setContentView((int) R.layout.day_layout);
        this.i = (RecyclerView) findViewById(R.id.recyclerAllDaysList);
        this.A = new Library(this);
        this.l = (Button) findViewById(R.id.buttonTwo);
        this.m = new LinearLayoutManager(this, 1, false);
        setAdmodAds();
        c();
        d();
        Bundle extras = getIntent().getExtras();
        this.o = extras.getString("day");
        TextView textView = (TextView) findViewById(R.id.mtoolbar_title1);
        textView.setText(this.o);
        this.u = extras.getInt("day_num");
        this.p = extras.getFloat(NotificationCompat.CATEGORY_PROGRESS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        ((TextView) toolbar.findViewById(R.id.mtoolbar_title)).setText(this.o);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DayActivity.this.finish();
            }
        });
        this.v = b();
        this.n = new IndividualDayAdapter(this, this.o, this.v, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        this.i.setLayoutManager(this.m);
        this.i.setAdapter(this.n);
        this.i.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.onItemClickListener() {
            public final void OnItem(View view, int i) {
                DayActivity.lambda$onCreate$1(DayActivity.this, view, i);
            }
        }));
        this.l.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DayActivity.lambda$onCreate$2(DayActivity.this, view);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }
}
