package com.example.android.quakereport;

/**
 * Created by srujana on 28/12/17.
 */

public class Earthquake {
    String magnitude;
    String date,place,tym;

    public Earthquake(String magnitude, String place, String date,String tym) {
        this.magnitude = magnitude;
        this.date = date;
        this.place = place;
        this.tym = tym;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getTym() {
        return tym;
    }
}
