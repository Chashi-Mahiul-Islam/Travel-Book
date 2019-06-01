package com.example.user.last_try;

/**
 * Created by User on 11/4/2017.
 */

public class RecyclerItem4 implements Comparable{

    private String description;
    private String image_path;
    private  int votes;
    private double latitude;
    private double longitude;
    private String address;
    private String category;
    private String ke_dise;
    public RecyclerItem4(String description, String image_path, double latitude, double longitude, String address, String category, String ke_dise, int votes) {



        this.description = description;
        this.image_path=image_path;

        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.category=category;
        this.ke_dise=ke_dise;
        this.votes=votes;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude=latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude=longitude;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category=category;
    }
    public String getKe_dise() {
        return ke_dise;
    }
public int getVotes(){return votes;}
public void setVotes(int votes){this.votes=votes;}
    public void setKe_dise(String ke_dise) {
        this.ke_dise=ke_dise;
    }
    @Override
    public int compareTo(Object another) {//search page e vote wise sort korbe

        if(((RecyclerItem4)another).votes > votes){
            return 1;
        }else if(((RecyclerItem4)another).votes==votes){
            return 0;
        }else{
            return -1;
        }


    }
}

