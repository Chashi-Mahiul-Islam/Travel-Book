package com.example.user.last_try;

/**
 * Created by User on 1/19/2018.
 */

public class help_me {

    public double longitude;
    public double latitude;
    public String name;
    public int position_number;
    public help_me() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public help_me(double longitude, double latitude, String name,int position_number) {

        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        this.position_number=position_number;
    }
}
