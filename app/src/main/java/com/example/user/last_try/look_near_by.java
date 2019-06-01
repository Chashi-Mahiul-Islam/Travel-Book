
package com.example.user.last_try;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class look_near_by extends AppCompatActivity implements View.OnClickListener {

    private ImageButton touristPlacesButton;
    private ImageButton restaurantsButton;
    private ImageButton hotelsButton;
    private ImageButton ATMButton;
    private ImageButton pharmaciesButton;
    private ImageButton gasStationsButton;
    FirebaseDatabase database;//add
    DatabaseReference myRef;//add
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_near_by);
        database = FirebaseDatabase.getInstance();//add
        myRef =database.getReference(MainActivity.usernames+"last_emergency");//add


        touristPlacesButton=(ImageButton) findViewById(R.id.touristPlacesButton);
        restaurantsButton=(ImageButton) findViewById(R.id.restaurantsButton);
        hotelsButton=(ImageButton) findViewById(R.id.hotelsButton);
        ATMButton=(ImageButton) findViewById(R.id.ATMButton);
        pharmaciesButton=(ImageButton) findViewById(R.id.pharmaciesButton);
        gasStationsButton=(ImageButton) findViewById(R.id.gasStationButton);

        touristPlacesButton.setOnClickListener(this);
        restaurantsButton.setOnClickListener(this);
        hotelsButton.setOnClickListener(this);
        ATMButton.setOnClickListener(this);
        pharmaciesButton.setOnClickListener(this);
        gasStationsButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view==touristPlacesButton)
        {
            touristPlaces();
        }
        else if(view==restaurantsButton)
        {
            restaurants();
        }
        else if(view==hotelsButton)
        {
            hotels();
        }
        else if(view==ATMButton)
        {
            ATMS();
        }
        else if(view==pharmaciesButton)
        {
            pharmacies();
        }
        else if(view==gasStationsButton)
        {
            gasStations();
        }

    }

    public void restaurants()
    {
        // Search for restaurants nearby
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void pharmacies() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=pharmacies");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void ATMS() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=ATM");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void gasStations() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=gas stations");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void hotels() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=hotels");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void touristPlaces() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=tourist spots");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void search_friend(View view){//add
        Intent intent=new Intent(this,search_friends.class);
        startActivity(intent);

    }
    public void clear_map_history(View view){//add
        myRef.setValue(null);
    }
    public void extreme_emergency(View view){
        Intent intent=new Intent(this,home_emergency.class);
        startActivity(intent);
    }
    public void look(View view){
        Intent intent=new Intent(this,Final_Look.class);
        startActivity(intent);
    }
}
