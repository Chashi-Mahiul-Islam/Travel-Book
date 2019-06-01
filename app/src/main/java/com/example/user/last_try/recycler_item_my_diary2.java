package com.example.user.last_try;

/**
 * Created by User on 11/9/2017.
 */

public class recycler_item_my_diary2 {
    public String description;

    public String image_path;
    public double latitude;
    public double longitude;
    public String place_name;
    public recycler_item_my_diary2() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public recycler_item_my_diary2(String description, String image_path, double latitude, double longitude, String place_name) {
       this.description=description;

       this.image_path=image_path;
       this.latitude=latitude;
       this.longitude=longitude;
       this.place_name=place_name;
    }
}
