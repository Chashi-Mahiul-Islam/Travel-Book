package com.example.user.last_try;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity_for_show2  extends FragmentActivity implements OnMapReadyCallback {
    //static String address;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_show2);
        // address=Myadapter2.address;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(Myadapter_for_multiple_journey.latitude,Myadapter_for_multiple_journey.longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(Myadapter_for_multiple_journey.address));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // if (marker.getTitle().equals("Chennai")){
                Toast.makeText(MapsActivity_for_show2.this, "Clicked"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),list_tester4.class);
                intent.putExtra("address",Myadapter_for_multiple_journey.address);
                startActivity(intent);
                //}
                return false;
            }
        });
        // Add a marker in Sydney and move the camera

    }
}
