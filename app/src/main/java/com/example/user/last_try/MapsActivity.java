package com.example.user.last_try;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
//ekjoner diary dekhar somoy spinner e map e click korle
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase database;
    private GoogleMap mMap;boolean is_prothom=false;
    LocationManager locationManager;
    LatLng lt1;DatabaseReference myRef,myRef2;
    Marker m;String database_path_for_vote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        database = FirebaseDatabase.getInstance();
        myRef2 =database.getReference(MyAdapter.database_key);//database key amanbangladesh
        myRef =database.getReference(MyAdapter.database_key+"map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent iin = getIntent();

        Bundle b = iin.getExtras();
        if (b != null) {
            database_path_for_vote = (String) b.get("database_path_for_vote");
            Toast.makeText(getApplicationContext(), "in maps activity"+database_path_for_vote, Toast.LENGTH_SHORT).show();
        }
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    return;
                }
                final map_co_ordinate Ekta= dataSnapshot.getValue(map_co_ordinate.class);
                if(list_tester2.kon_din==Ekta.day) {
                    double latitude = Ekta.latitude;
                    double longitude = Ekta.longitude;
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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18f));//zoom out
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

        ChildEventListener childEventListener2 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                final ekta_event Ekta= dataSnapshot.getValue(ekta_event.class);
                if(list_tester2.kon_din==Ekta.day) {
                    double latitude = Ekta.latitude;
                    double longitude = Ekta.longitude;
                    String str=Ekta.place_name;
                    LatLng latlng = new LatLng(latitude, longitude);
                    m=mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                    m.showInfoWindow();
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
        myRef.addChildEventListener(childEventListener);
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
                Toast.makeText(MapsActivity.this, "Clicked"+marker.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),list_tester4.class);
                intent.putExtra("address",marker.getTitle());
                startActivity(intent);
                return false;
            }
        });


    }
}
