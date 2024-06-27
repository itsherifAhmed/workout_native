package com.sherif.womenabsworkoutsecit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sherif.womenabsworkoutsecit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String EXC_DAY_TABLE = "exc_day";
    public static String EXC_DAY_TABLE_ADVANCED = "exc_advanced";

    /* renamed from: a  reason: collision with root package name */
    public static int f883a = 4;
    public static String b = "FitDB";
    public static String c = "day";
    public static String d = "progress";
    public static String e = "counter";
    public static String f = "cycles";
    public static String g = ("CREATE TABLE " + EXC_DAY_TABLE + " (" + c + " TEXT, " + d + " REAL, " + e + " INTEGER, " + f + " TEXT)");
    public static String h = ("CREATE TABLE " + EXC_DAY_TABLE_ADVANCED + " (" + c + " TEXT, " + d + " REAL, " + e + " INTEGER, " + f + " TEXT)");
    public Context i;
    public int[] j = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public int[] k = {R.array.adv_day1_cycles, R.array.adv_day2_cycles, R.array.adv_day3_cycles, R.array.adv_day4_cycles, R.array.adv_day5_cycles, R.array.adv_day6_cycles, R.array.adv_day7_cycles, R.array.adv_day8_cycles, R.array.adv_day9_cycles, R.array.adv_day10_cycles, R.array.adv_day11_cycles, R.array.adv_day12_cycles, R.array.adv_day13_cycles, R.array.adv_day14_cycles, R.array.adv_day15_cycles, R.array.adv_day16_cycles, R.array.adv_day17_cycles, R.array.adv_day18_cycles, R.array.adv_day19_cycles, R.array.adv_day20_cycles, R.array.adv_day21_cycles, R.array.adv_day22_cycles, R.array.adv_day23_cycles, R.array.adv_day24_cycles, R.array.adv_day25_cycles, R.array.adv_day26_cycles, R.array.adv_day27_cycles, R.array.adv_day28_cycles, R.array.adv_day29_cycles, R.array.adv_day30_cycles};
    public String l = "'Day ";
//    public String m = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    public String n = "0.0";
//    public String o = AppEventsConstants.EVENT_PARAM_VALUE_NO;

    public DatabaseHelper(Context context) {
        super(context, b, (SQLiteDatabase.CursorFactory) null, f883a);
        this.i = context;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(g);
            Log.d("TAG", "table1 created");
            sQLiteDatabase.execSQL(h);
            Log.d("TAG", "table2 created");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        Log.i("dbpath: ", "path: " + sQLiteDatabase.getPath());
        if (i2 == 3 || i2 == 4) {
            sQLiteDatabase.execSQL("ALTER TABLE " + EXC_DAY_TABLE + " ADD COLUMN " + f + " TEXT");
            sQLiteDatabase.execSQL("ALTER TABLE " + EXC_DAY_TABLE_ADVANCED + " ADD COLUMN " + f + " TEXT");
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                for (int i4 = 1; i4 <= 30; i4++) {
                    int i5 = i4 - 1;
                    int[] intArray = this.i.getResources().getIntArray(this.j[i5]);
                    int[] intArray2 = this.i.getResources().getIntArray(this.k[i5]);
                    int i6 = 0;
                    for (int put : intArray) {
                        try {
                            jSONObject.put(String.valueOf(i6), put);
                            i6++;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    int i7 = 0;
                    for (int put2 : intArray2) {
                        try {
                            jSONObject2.put(String.valueOf(i7), put2);
                            i7++;
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                    }
                    Log.e("TAG", "json str databs: " + jSONObject.toString());
                    Log.e("TAG", "json str databs: " + jSONObject2.toString());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(f, jSONObject.toString());
                    if (sQLiteDatabase != null) {
                        try {
                            long update = (long) sQLiteDatabase.update(EXC_DAY_TABLE, contentValues, c + "='Day " + i4 + "'", (String[]) null);
                            StringBuilder sb = new StringBuilder();
                            sb.append("res: ");
                            sb.append(update);
                            Log.e("TAG", sb.toString());
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put(f, jSONObject2.toString());
                    if (sQLiteDatabase != null) {
                        try {
                            long update2 = (long) sQLiteDatabase.update(EXC_DAY_TABLE_ADVANCED, contentValues2, c + "='Day " + i4 + "'", (String[]) null);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("res: ");
                            sb2.append(update2);
                            Log.e("TAG", sb2.toString());
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            Log.e("TAG", "Case 3 db");
        }
    }
}
