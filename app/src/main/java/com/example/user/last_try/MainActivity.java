package com.example.user.last_try;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    user[] S = new user[10000];static String k[];

    int i = 0;static ekta_event So[];
    static String kare_khujo;//="amanargntina";
    int koyta_user;static int koytachild;
    static String usernames=new String();//amr nijer user name
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference users, koytauser;
    EditText username, passsword;
    Button login, signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        users = database.getReference("users");
        username = (EditText) findViewById(R.id.username);
        passsword = (EditText) findViewById(R.id.password);



        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "everyone is safe\n", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                S[i] = dataSnapshot.getValue(user.class);//hashmap
                // Toast.makeText(getApplicationContext(), S[i].username, Toast.LENGTH_SHORT).show();

                i++;

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        users.addChildEventListener(childEventListener);


    }


    public void signup(View view) {
        Intent intent=new Intent(MainActivity.this,register.class);
        startActivity(intent);

    }

    public void signin(View view){

        String name=username.getText().toString();
        String pass=passsword.getText().toString();
        for(int k=0;k<i;k++){
            if(S[k].username.equals(name)){
                if(!(S[k].password.equals(pass))){
                    //  Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    passsword.setText("");

                    return;

                }else{
                    //  Toast.makeText(getApplicationContext(), "successfully logged in", Toast.LENGTH_SHORT).show();
                    MainActivity.usernames=name;
                    Intent intent=new Intent(MainActivity.this,Mainpage.class);
                    //   Intent intent=new Intent(MainActivity.this,profileActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        }
        Toast.makeText(getApplicationContext(), "this username dont exist", Toast.LENGTH_SHORT).show();

    }


    public void map(View view){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
        if (launchIntent != null) {
            //   Toast.makeText(getApplicationContext(), "gt pyse", Toast.LENGTH_SHORT).show();
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
        else
        {
            Toast.makeText(getApplicationContext(), "gt painy", Toast.LENGTH_SHORT).show();
        }
    }

}
