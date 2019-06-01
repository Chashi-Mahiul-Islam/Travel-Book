package com.example.user.last_try;

/**
 * Created by User on 1/19/2018.
 */

public class ekta_jaygar_asepase_ke_gese_ki_likhse {
    public String text;
    public String image_path;
    public String category;
    public double latitude;
    public double longitude;
    public String place_name;
    public String ke_gese;

    public ekta_jaygar_asepase_ke_gese_ki_likhse() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ekta_jaygar_asepase_ke_gese_ki_likhse(String text, String image_path,String category,double latitude,double longitude,String place_name,String ke_gese) {
        this.text = text;
        this.image_path = image_path;
        this.category=category;
        this.latitude=latitude;
        this.longitude=longitude;
        this.place_name=place_name;
        this.ke_gese=ke_gese;
    }
}
