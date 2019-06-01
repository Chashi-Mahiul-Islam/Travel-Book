package com.example.user.last_try;

/**
 * Created by User on 11/5/2017.
 */

public class RecyclerItemForMultipleJourney {

    private String description;
    private String image_path;
    private String database_path;
    private double latitude;
    private double longitude;
    private String address;
    private String ke_dise;
    public RecyclerItemForMultipleJourney(String description, String image_path, String database_path, double latitude, double longitude, String address, String ke_dise) {

        this.description = description;

        this.image_path=image_path;
        this.database_path=database_path;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.ke_dise=ke_dise;
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
    public String getDatabase_path() {
        return database_path;
    }

    public void setDatabase_path(String database_path) {
        this.database_path = database_path;
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
    public String getKe_dise() {
        return ke_dise;
    }

    public void setKe_dise(String ke_dise) {
        this.ke_dise=ke_dise;
    }
}
