package com.sherif.womenabsworkoutsecit.utils;

public class MealsItemObject {
    public String name;
    public int photo;

    public MealsItemObject(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
