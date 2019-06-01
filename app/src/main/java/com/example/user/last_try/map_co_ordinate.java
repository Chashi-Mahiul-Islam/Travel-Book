package com.example.user.last_try;

/**
 * Created by User on 11/2/2017.
 */

public class map_co_ordinate {
    public double longitude;
    public double latitude;
    public int day;
    public String kar_co_ordinate;
    public map_co_ordinate() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public map_co_ordinate(double longitude,double latitude,int day,String kar_co_ordinate) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.day=day;
        this.kar_co_ordinate=kar_co_ordinate;
    }
}
