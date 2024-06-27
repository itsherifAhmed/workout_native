package com.sherif.womenabsworkoutsecit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sherif.womenabsworkoutsecit.R;
import com.sherif.womenabsworkoutsecit.adapters.WorkoutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    /* renamed from: a  reason: collision with root package name */
    public Context f884a;
    public int[] b;
    public int[] c;
    public int[] d = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public DatabaseHelper dbHelper;
    public int[] e = {R.array.adv_day1_cycles, R.array.adv_day2_cycles, R.array.adv_day3_cycles, R.array.adv_day4_cycles, R.array.adv_day5_cycles, R.array.adv_day6_cycles, R.array.adv_day7_cycles, R.array.adv_day8_cycles, R.array.adv_day9_cycles, R.array.adv_day10_cycles, R.array.adv_day11_cycles, R.array.adv_day12_cycles, R.array.adv_day13_cycles, R.array.adv_day14_cycles, R.array.adv_day15_cycles, R.array.adv_day16_cycles, R.array.adv_day17_cycles, R.array.adv_day18_cycles, R.array.adv_day19_cycles, R.array.adv_day20_cycles, R.array.adv_day21_cycles, R.array.adv_day22_cycles, R.array.adv_day23_cycles, R.array.adv_day24_cycles, R.array.adv_day25_cycles, R.array.adv_day26_cycles, R.array.adv_day27_cycles, R.array.adv_day28_cycles, R.array.adv_day29_cycles, R.array.adv_day30_cycles};
    public SQLiteDatabase sqlite;

    public DatabaseOperations(Context context) {
        this.f884a = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public int CheckDBEmpty(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        Cursor rawQuery = this.sqlite.rawQuery("SELECT count(*) FROM " + str, (String[]) null);
        rawQuery.moveToFirst();
        return rawQuery.getInt(0) > 0 ? 1 : 0;
    }

    public void createAdvTable() {
        try {
            SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
            DatabaseHelper databaseHelper = this.dbHelper;
            writableDatabase.execSQL(DatabaseHelper.h);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    public int deleteTable() {
        this.sqlite = this.dbHelper.getWritableDatabase();
        int delete = this.sqlite.delete(DatabaseHelper.c, null, null);
        this.sqlite.close();
        return delete;
    }

    public List<WorkoutData> getAllDaysProgress() {
        ArrayList arrayList = new ArrayList();
        this.sqlite = this.dbHelper.getReadableDatabase();
        try {
            if (this.sqlite != null) {
                SQLiteDatabase sQLiteDatabase = this.sqlite;
                Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE, (String[]) null);
                if (rawQuery.moveToFirst()) {
                    while (!rawQuery.isAfterLast()) {
                        WorkoutData workoutData = new WorkoutData();
                        workoutData.setDay(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.c)));
                        workoutData.setProgress(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.d)));
                        rawQuery.moveToNext();
                        arrayList.add(workoutData);
                    }
                }
                rawQuery.close();
                this.sqlite.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public List<WorkoutData> getAllDaysProgressAdv() {
        ArrayList arrayList = new ArrayList();
        this.sqlite = this.dbHelper.getReadableDatabase();
        try {
            if (this.sqlite != null) {
                SQLiteDatabase sQLiteDatabase = this.sqlite;
                Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED, (String[]) null);
                if (rawQuery.moveToFirst()) {
                    while (!rawQuery.isAfterLast()) {
                        WorkoutData workoutData = new WorkoutData();
                        workoutData.setDay(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.c)));
                        workoutData.setProgress(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.d)));
                        rawQuery.moveToNext();
                        arrayList.add(workoutData);
                    }
                }
                rawQuery.close();
                this.sqlite.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public int getDayExcCounter(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        int i = 0;
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.e));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return i;
    }

    public int getDayExcCounterAdv(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        int i = 0;
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.e));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return i;
    }

    public String getDayExcCycles(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        String str2 = "";
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                str2 = rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.f));
            }
            this.sqlite.close();
        }
        return str2;
    }

    public String getDayExcCyclesAdv(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        String str2 = "";
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                str2 = rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.f));
            }
            this.sqlite.close();
        }
        return str2;
    }

    public float getExcDayProgress(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        float f = 0.0f;
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                f = rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.d));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return f;
    }

    public float getExcDayProgressAdv(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        float f = 0.0f;
        if (sQLiteDatabase != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED + " where " + DatabaseHelper.c + " = '" + str + "'", (String[]) null);
            if (rawQuery.moveToFirst()) {
                f = rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.d));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return f;
    }

    public long insertExcALLDayData() {
        long j = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            for (int i = 1; i <= 30; i++) {
                JSONObject jSONObject = new JSONObject();
                this.b = this.f884a.getResources().getIntArray(this.d[i - 1]);
                int i2 = 0;
                for (int put : this.b) {
                    try {
                        jSONObject.put(String.valueOf(i2), put);
                        i2++;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                Log.e("TAG", "json str databs: " + jSONObject.toString());
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.c, "Day " + i);
                contentValues.put(DatabaseHelper.d, Double.valueOf(0.0d));
                contentValues.put(DatabaseHelper.e, 0);
                contentValues.put(DatabaseHelper.f, jSONObject.toString());
                if (this.sqlite != null) {
                    try {
                        j = this.sqlite.insert(DatabaseHelper.EXC_DAY_TABLE, (String) null, contentValues);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            this.sqlite.close();
        } catch (Exception e4) {
            e4.printStackTrace();
            this.sqlite.close();
        }
        return j;
    }

    public long insertExcALLDayDataAdv() {
        long j = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            for (int i = 1; i <= 30; i++) {
                JSONObject jSONObject = new JSONObject();
                this.c = this.f884a.getResources().getIntArray(this.e[i - 1]);
                int i2 = 0;
                for (int put : this.c) {
                    try {
                        jSONObject.put(String.valueOf(i2), put);
                        i2++;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                Log.e("TAG", "json str databs: " + jSONObject.toString());
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.c, "Day " + i);
                contentValues.put(DatabaseHelper.d, Double.valueOf(0.0d));
                contentValues.put(DatabaseHelper.e, 0);
                contentValues.put(DatabaseHelper.f, jSONObject.toString());
                if (this.sqlite != null) {
                    try {
                        j = this.sqlite.insert(DatabaseHelper.EXC_DAY_TABLE_ADVANCED, (String) null, contentValues);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            this.sqlite.close();
        } catch (Exception e4) {
            e4.printStackTrace();
            this.sqlite.close();
        }
        return j;
    }

    public int insertExcCounter(String str, int i) {
        int i2 = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.e, Integer.valueOf(i));
            if (this.sqlite != null) {
                try {
//                    "UPDATE " + DatabaseHelper.EXC_DAY_TABLE + " SET " + DatabaseHelper.e + " = " + i + " WHERE " + DatabaseHelper.c + " = '" + str + "'";
                    i2 = this.sqlite.update(DatabaseHelper.EXC_DAY_TABLE, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i2;
    }

    public int insertExcCounterAdv(String str, int i) {
        int i2 = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.e, Integer.valueOf(i));
            if (this.sqlite != null) {
                try {

//                    "UPDATE " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED + " SET " + DatabaseHelper.e + " = " + i + " WHERE " + DatabaseHelper.c + " = '" + str + "'";
                    i2 = this.sqlite.update(DatabaseHelper.EXC_DAY_TABLE_ADVANCED, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i2;
    }

    public int insertExcCycles(String str, String str2) {
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.f, str2);
            if (this.sqlite != null) {
                try {
//                    "UPDATE " + DatabaseHelper.EXC_DAY_TABLE + " SET " + DatabaseHelper.f + " = " + str2 + " WHERE " + DatabaseHelper.c + " = '" + str + "'";
                    i = this.sqlite.update(DatabaseHelper.EXC_DAY_TABLE, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public int insertExcCyclesAdv(String str, String str2) {
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.f, str2);
            if (this.sqlite != null) {
                try {
//                     "UPDATE " + DatabaseHelper.EXC_DAY_TABLE_ADVANCED + " SET " + DatabaseHelper.f + " = " + str2 + " WHERE " + DatabaseHelper.c + " = '" + str + "'";
                    i = this.sqlite.update(DatabaseHelper.EXC_DAY_TABLE_ADVANCED, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public int insertExcDayData(String str, float f) {
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.d, Float.valueOf(f));
            if (this.sqlite != null) {
                try {
                    SQLiteDatabase sQLiteDatabase = this.sqlite;
                    String str2 = DatabaseHelper.EXC_DAY_TABLE;
                    i = sQLiteDatabase.update(str2, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public int insertExcDayDataAdv(String str, float f) {
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.d, Float.valueOf(f));
            if (this.sqlite != null) {
                try {
                    SQLiteDatabase sQLiteDatabase = this.sqlite;
                    String str2 = DatabaseHelper.EXC_DAY_TABLE_ADVANCED;
                    i = sQLiteDatabase.update(str2, contentValues, DatabaseHelper.c + "='" + str + "'", (String[]) null);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public boolean tableExists(String str) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (str == null || writableDatabase == null || !writableDatabase.isOpen()) {
            return false;
        }
        Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", str});
        if (!rawQuery.moveToFirst()) {
            rawQuery.close();
            return false;
        }
        int i = rawQuery.getInt(0);
        rawQuery.close();
        return i > 0;
    }
}
