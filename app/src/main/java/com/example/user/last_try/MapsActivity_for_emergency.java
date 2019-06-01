package com.example.user.last_try;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity_for_emergency extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;boolean is_prothom=false;
    LatLng lt1;DatabaseReference myRef;
    FirebaseDatabase database;
    MarkerOptions marker;Marker m;boolean first=true;LatLng sydney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_for_emergency);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        database = FirebaseDatabase.getInstance();
        //myRef=database.getReference(Mainpage.help_friend_database_address);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "everyone is safe\n", Toast.LENGTH_SHORT).show();
                    return;
                }
                final help_me Ekta= dataSnapshot.getValue(help_me.class);
                double latitude = Ekta.latitude;
                double longitude = Ekta.longitude;
                if(latitude==0||longitude==0){return;}
                LatLng latlng = new LatLng(latitude, longitude);
                if (is_prothom == false) {
                    is_prothom = true;
                    lt1 = latlng;
                }
                else {
                    PolylineOptions poption = new PolylineOptions().add(lt1).add(latlng).width(20).color(Color.BLUE).geodesic(true);
                    mMap.addPolyline(poption);
                    lt1 = latlng;
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16f));//zoom out

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Mainpage.kar_help_lagbe_tar_last_emergency.addChildEventListener(childEventListener);

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

    }
}
