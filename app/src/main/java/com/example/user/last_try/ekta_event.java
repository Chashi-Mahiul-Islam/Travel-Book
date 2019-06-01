package com.example.user.last_try;

public class ekta_event {//basic event jetay pic,text sob add hoy
    public String text;
    public String image_path;
    public String category;
    public int day;
    public double latitude;
    public double longitude;
    public String place_name;
    public String ke_dise;

    public ekta_event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ekta_event(String text, String image_path,String category,int day,double latitude,double longitude,String place_name,String ke_dise) {
        this.text = text;
        this.image_path = image_path;
        this.category=category;
        this.day=day;
        this.latitude=latitude;
        this.longitude=longitude;
        this.place_name=place_name;
        this.ke_dise=ke_dise;

    }
}
