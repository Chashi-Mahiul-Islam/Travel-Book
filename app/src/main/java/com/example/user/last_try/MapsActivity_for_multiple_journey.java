package com.example.user.last_try;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
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

public class MapsActivity_for_multiple_journey extends FragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase database;
    private GoogleMap mMap;boolean is_prothom=false;
    LocationManager locationManager;
    LatLng lt1;DatabaseReference myRef,myRef2;
    Marker m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        database = FirebaseDatabase.getInstance();
        myRef2 =database.getReference(MyAdapter.database_key);
        myRef =database.getReference(MyAdapter.database_key+"map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(list_tester_for_multiple_contributor_journey.kar_contribution.contains("all")==false) {
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                        return;
                    }
                    final map_co_ordinate Ekta = dataSnapshot.getValue(map_co_ordinate.class);
                    if (list_tester_for_multiple_contributor_journey.kon_din == Ekta.day) {
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
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18f));
                    }


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
            myRef.addChildEventListener(childEventListener);
        }

        ChildEventListener childEventListener2 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                final ekta_event Ekta= dataSnapshot.getValue(ekta_event.class);
                if(list_tester_for_multiple_contributor_journey.kon_din==Ekta.day&&(list_tester_for_multiple_contributor_journey.kar_contribution.contains("all")||list_tester_for_multiple_contributor_journey.kar_contribution.contains(Ekta.ke_dise))) {
                    double latitude = Ekta.latitude;
                    double longitude = Ekta.longitude;
                    if(latitude==0||longitude==0){return;}
                    String str=Ekta.place_name;
                    LatLng latlng = new LatLng(latitude, longitude);


                    mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                    // m.showInfoWindow();
                    // mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10.2f));

                }



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
        myRef2.addChildEventListener(childEventListener2);
        if(list_tester_for_multiple_contributor_journey.kar_contribution.contains("all")==false){

        }
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // if (marker.getTitle().equals("Chennai")){
                Toast.makeText(MapsActivity_for_multiple_journey.this, "Clicked"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),list_tester4.class);
                intent.putExtra("address",marker.getTitle());
                startActivity(intent);
                //}
                return false;
            }
        });


    }
}
